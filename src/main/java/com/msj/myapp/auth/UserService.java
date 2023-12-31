package com.msj.myapp.auth;


import com.msj.myapp.auth.util.Hash;
import com.msj.myapp.program.Program;
import com.msj.myapp.program.ProgramRepository;
import com.msj.myapp.user.User;
import com.msj.myapp.user.UserRepository;
import com.msj.myapp.user.request.SignupRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private ProgramRepository programRepo;
    private UserRepository repo;
    @Autowired
    Hash hash;

    @Autowired
    public UserService(UserRepository repo, ProgramRepository programRepo){
        this.repo = repo;
        this.programRepo = programRepo;
    }

    @Transactional
//    HTML에서 받은 양식을 고대로 가져 옴
    public long createIdentity(SignupRequest req){
        System.out.println("create아이덴티티" + req);
        User toSaveUser = User.builder()
                .name(req.getName())  //html에서 입력 받은 signupRequest 객체의 필드를 get
                .sex(req.getSex())
                .age(req.getAge())
                .phone(req.getPhone())
                .goalCal(req.getGoalCal())
                .height(req.getHeight())
                .weight(req.getWeight())
                .userChoiceLevel(req.getUserChoiceLevel())
                .activity(req.getActivity())
                .userChoiceGoal(req.getUserChoiceGoal())
                .secret(hash.createHash(req.getPassword()))
                .programName(req.getProgramTitle())
                .build();
        System.out.println(req);
        User savedUser = repo.save(toSaveUser);


        Optional<Program> program = programRepo.findByProgramTitle(req.getProgramTitle());
        if (program.isPresent()) {
            savedUser.setProgram(program.get()); // 프로그램을 유저에 연결
            repo.save(savedUser);
        }

        return savedUser.getId();
    }

}

