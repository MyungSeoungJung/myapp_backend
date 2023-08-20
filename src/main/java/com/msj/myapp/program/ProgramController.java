package com.msj.myapp.program;


import com.msj.myapp.auth.Auth;
import com.msj.myapp.auth.AuthProfile;
import com.msj.myapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @GetMapping (value = "/recommendProgram")
    public ResponseEntity<List<Program>> recommendProgram () {
        List<Program> programs = programRepository.findAll(Sort.by("id").ascending());
        return ResponseEntity.status(HttpStatus.OK).body(programs);
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

    @GetMapping
    public ResponseEntity<List<Program>> getProgram() {
        List<Program> program = programRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(program);
    }

    @GetMapping ("/choiceProgram")
    public ResponseEntity<List<Program>> choiceProgram(){
        return null;
    }

}
