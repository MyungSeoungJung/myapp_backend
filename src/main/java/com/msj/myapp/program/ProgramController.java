package com.msj.myapp.program;


import com.msj.myapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/program")
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    UserRepository repo;


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
