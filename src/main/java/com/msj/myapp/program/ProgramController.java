package com.msj.myapp.program;


import com.msj.myapp.auth.Auth;
import com.msj.myapp.auth.AuthProfile;
import com.msj.myapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    UserRepository repo;

    @Autowired
    ProgramCommentRepository programCommentRepository;
    @Autowired
    ProgramService service;
    @Auth
    @GetMapping(value = "/myExercise")
    public ResponseEntity<Map<String,Object>> myExercise (@RequestAttribute AuthProfile authProfile){
    Map<String,Object> res = new HashMap<>();
    Optional<Program> matchProgram = programRepository.findByProgramTitle(authProfile.getProgramName());
    res.put("programTitle",matchProgram.get().getProgramTitle());
    res.put("programGoal",matchProgram.get().getProgramGoal());
    res.put("programLevel",matchProgram.get().getProgramLevel());
    res.put("programIntro",matchProgram.get().getProgramIntro());
    res.put("programImg",matchProgram.get().getImg());
    res.put("programRate",matchProgram.get().getRate());
    res.put("coachName",matchProgram.get().getCoachName());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @GetMapping (value = "/recommendProgram")
    public ResponseEntity<List<Program>> recommendProgram (@RequestParam String goal) {
//        List<Program> programs = programRepository.findByProgramGoal(program);
        List<Program> programs = programRepository.findAll(Sort.by("id").ascending());

        return ResponseEntity.status(HttpStatus.OK).body(programs);
    }

    @GetMapping (value = "/detailProgram")
    public ResponseEntity<Map<String,Object>> detailProgram (@RequestParam long id) {
        System.out.println(id);
        Optional<Program> matchProgram = programRepository.findById(id);
        Map<String,Object> res = new HashMap<>();
        res.put("programTitle",matchProgram.get().getProgramTitle());
        res.put("programGoal",matchProgram.get().getProgramGoal());
        res.put("programLevel",matchProgram.get().getProgramLevel());
        res.put("programIntro",matchProgram.get().getProgramIntro());
        res.put("programImg",matchProgram.get().getImg());
        res.put("programRate",matchProgram.get().getRate());
        res.put("coachName",matchProgram.get().getCoachName());


        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @PostMapping ("/addProgram")
    public ResponseEntity addProgram(@RequestBody Program program){
        System.out.println(program);
        Program savedProgram =  programRepository.save(program);
        Map<String,Object> res = new HashMap<>();
        res.put("data",savedProgram);
        res.put("message","created");
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @GetMapping ("/choiceProgram")
    public ResponseEntity<List<Program>> choiceProgram(){
        return null;
    }

//페이징------------------------------------------------------------
    @GetMapping(value = "/getProgram")
    public ResponseEntity<List<Program>> getProgram() {
        List<Program> program = programRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(program);
    }

    @GetMapping(value = "/paging")
    public Page<Program> getPostsPaging(@RequestParam int page, @RequestParam int size) {
        System.out.println(page + "1");
        System.out.println(size + "1");

        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return programRepository.findAll(pageRequest);
    }

    //No가 포함된 목록 조회
    @GetMapping(value = "/paging/search")
    public Page<Program> getPostPagingSearch(@RequestParam int page,@RequestParam int size,String query){
        Sort sort = Sort.by("id").descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
//    작성자 타이틀 출력
        System.out.println(query); ///
        System.out.println(query); ///
        return programRepository.findByProgramTitleContains(query,pageRequest);
    }


//    프로그램 리뷰 달기
@Auth
@PostMapping("/comments")
public ResponseEntity addComments(
        @RequestParam long id,
        @RequestBody ProgramComment programComment,
        @RequestAttribute AuthProfile authProfile) {

    Optional<Program> matchprogram = programRepository.findById(id);
    if(!matchprogram.isPresent()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // 커멘트 추가
    programComment.setUserId(authProfile.getId());
    programComment.setUserSex(authProfile.getSex());
    programComment.setUserName(authProfile.getName());
    programComment.setUserAge(authProfile.getAge());
    programComment.getContent();
    programComment.setProgram(matchprogram.get()); //  댓글 데이터베이스에 URL엔드포인트 파라미터로 받은 id 찾아서 할당

    // 트랜잭션 처리 x = 데이터베이스에 저장 태랜잭션은 나중에
    service.createComment(programComment);

    Map<String,Object> res = new HashMap<>();
    res.put("userId",programComment.getUserId());
    res.put("content",programComment.getContent());
    res.put("userName",programComment.getUserName());
    res.put("userSex",programComment.getUserSex());
    res.put("userAge",programComment.getUserAge());
    return ResponseEntity.status(HttpStatus.CREATED).body(res);
}

// 프로그램 리뷰 띄우기
    @GetMapping ("/getComment")
    public ResponseEntity<List<ProgramComment>> getComment (@RequestParam long id) {
//        id로 일치하는 program 찾고
        Optional<Program> program = programRepository.findById(id);
// 찾은 프로그램에서 comment데이터베이스에서 다시 일치하는 애 찾고

        List<ProgramComment> programComments =   programCommentRepository.findByProgram(program);
//        찾은애를 이제 리스트로 배출
        return ResponseEntity.status(HttpStatus.OK).body(programComments);
    }
}



