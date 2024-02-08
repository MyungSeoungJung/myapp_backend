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
public class AuthService {
    private  ProgramRepository programRepo;
    private UserRepository userRepository;
    @Autowired
    Hash hash;

    @Autowired
    public AuthService(UserRepository userRepo, ProgramRepository programRepo){
        this.userRepository = userRepo;
        this.programRepo = programRepo;
    }

    @Transactional
//  회원가입
    public long createIdentity(SignupRequest req){
        User toSaveUser = User.builder()
                .name(req.getName())
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
        User savedUser = userRepository.save(toSaveUser);


        Optional<Program> program = programRepo.findByProgramTitle(req.getProgramTitle());
        if (program.isPresent()) {
            savedUser.setProgram(program.get()); // 프로그램을 유저에 연결
            userRepository.save(savedUser);
        }

        return savedUser.getId();
    }

}

