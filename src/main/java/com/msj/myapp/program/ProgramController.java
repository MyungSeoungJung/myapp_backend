package com.msj.myapp.program;
import com.msj.myapp.auth.Auth;
import com.msj.myapp.auth.AuthProfile;
import com.msj.myapp.programComment.ProgramComment;
import com.msj.myapp.programComment.ProgramCommentRepository;
import com.msj.myapp.user.User;
import com.msj.myapp.user.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "운동 프로그램 관리 처리")
@RestController
@RequestMapping("/program")
public class ProgramController {
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Program program;
    @Autowired
    ProgramCommentRepository programCommentRepository;
    @Autowired
    ProgramService service;
    @Autowired
    ProgramComment programComment;

    @Operation(summary = "내가 선택한 운동 프로그램", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping(value = "/myExercise")
    public ResponseEntity<Map<String,Object>> myExercise (@RequestAttribute AuthProfile authProfile){
    Optional<User> user = userRepository.findById(authProfile.getId());  // 유저 id 매칭
    Optional<Program> matchProgram = programRepository.findByProgramTitle(user.get().getProgramName());
     if (matchProgram.isPresent()){
        Map<String,Object> res = program.createProgramResponse(matchProgram.get());
        return ResponseEntity.status(HttpStatus.OK).body(res);
     }else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    }

    @Operation(summary = "추천 운동 프로그램 띄우기")
    @GetMapping (value = "/recommendProgram")
    public ResponseEntity<List<Program>> recommendProgram (@RequestParam String goal) {
        List<Program> programs = programRepository.findByProgramGoal(goal);
        return ResponseEntity.status(HttpStatus.OK).body(programs);
    }

    @Operation(summary = "운동 프로그램 띄우기")
    @GetMapping (value = "/bestProgram")
    public ResponseEntity<List<Program>> bestProgram () {
        List<Program> programs = programRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.status(HttpStatus.OK).body(programs);
    }

    @Operation(summary = "각각의 운동 프로그램 페이지")
    @GetMapping (value = "/detailProgram")
    public ResponseEntity<Map<String,Object>> detailProgram (@RequestParam long id) {
        Optional<Program> matchID = programRepository.findById(id);
        if (matchID.isPresent()){
            Map<String,Object> res = program.createProgramResponse(matchID.get());
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "운동 프로그램 추가하기")
    @PostMapping ("/addProgram")
    public ResponseEntity addProgram(@RequestBody Program program){
        Program savedProgram =  programRepository.save(program);
        Map<String,Object> res = new HashMap<>();
        res.put("data",savedProgram);
        res.put("message","created");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @Operation(summary = "운동 프로그램 띄우기")
    @GetMapping(value = "/getProgram")
    public ResponseEntity<List<Program>> getProgram() {
        List<Program> program = programRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(program);
    }
    @Operation(summary = "운동 프로그램 페이징")
    @GetMapping(value = "/paging")
    public Page<Program> getPostsPaging(@RequestParam int page, @RequestParam int size) {
        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return programRepository.findAll(pageRequest);
    }

    @Operation(summary = "운동 프로그램 검색")
    //No가 포함된 목록 조회
    @GetMapping(value = "/paging/search")
    public Page<Program> getPostPagingSearch(@RequestParam int page,@RequestParam int size,String query){
        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return programRepository.findByProgramTitleContains(query,pageRequest);
    }

    @Operation(summary = "운동 프로그램 리뷰 작성", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @PostMapping("/comments")
    public ResponseEntity addComments(@RequestParam long id,
                                      @RequestBody ProgramComment programComment,
                                      @RequestAttribute AuthProfile authProfile) {
        
    Optional<Program> matchProgram = programRepository.findById(id);
    Optional<User> user = userRepository.findById(authProfile.getId());
        if (matchProgram.isEmpty() || user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    ProgramComment newComment = programComment.createProgramComment(programComment, matchProgram.get(), user.get()); // 댓글 생성
    service.createComment(newComment);  // 서비스 로직
    Map<String, Object> res = programComment.createCommentResponse(newComment); // 댓글 생성 응답 반환
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


    @Operation(summary = "운동 프로그램 댓글 띄우기")
    @GetMapping ("/getComment")
    public ResponseEntity<List<ProgramComment>> getComment (@RequestParam long id) {
        Optional<Program> program = programRepository.findById(id);
        List<ProgramComment> programComments = programCommentRepository.findByProgram(program);
        return ResponseEntity.status(HttpStatus.OK).body(programComments);
    }

    @Operation(summary = "내가 작성한 운동 프로그램 리뷰", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping (value = "/myComment")
    public List<ProgramComment> getPostList(@RequestAttribute AuthProfile authProfile) {
        List<ProgramComment> list = programCommentRepository.findByUserId(authProfile.getId());
        return list;
    }

    @Operation(summary = "내가 선택한 운동 프로그램 변경", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @PostMapping(value = "/changeProgram")  // 추천 프로그램창 선택
    public ResponseEntity selectProgram(
            @RequestAttribute AuthProfile authProfile,
            @RequestBody Program program
    ) {
        long userId = authProfile.getId();
        String newProgramTitle = program.getProgramTitle();
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User findUser = user.get();
            Optional<Program> modifyProgram = programRepository.findByProgramTitle(newProgramTitle);
            if (modifyProgram.isPresent()) {
                findUser.setProgramName(newProgramTitle);
                userRepository.save(findUser);
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    }
