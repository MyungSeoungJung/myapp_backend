package com.msj.myapp.myapp.myCoach;


import com.msj.myapp.myapp.myCoach.MyCoachutil.Hash;
import com.msj.myapp.myapp.myCoach.entity.Program;
import com.msj.myapp.myapp.myCoach.entity.ProgramRepository;
import com.msj.myapp.myapp.myCoach.entity.User;
import com.msj.myapp.myapp.myCoach.entity.UserRepository;
import com.msj.myapp.myapp.myCoach.request.signupRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository repo;
    private ProgramRepository programRepo;

    @Autowired
    Hash hash;

    @Autowired
    //데이터 베이스와 접촉하기 위해? 생성자는 객체를 초기화해주기 위해서인데 이해가 안감
    public UserService(UserRepository repo, ProgramRepository profileRepo){
        this.repo = repo;
        this.programRepo = programRepo;
    }

    @Transactional
//    HTML에서 받은 양식을 고대로 가져 옴
    public long createIdentity(signupRequest req){
        User toSaveUser = User.builder()
                .name(req.getName())  //html에서 입력 받은 signupRequest 객체의 필드를 get
                .sex(req.getSex())
                .age(req.getAge())
                .phone(req.getPhone())
                .height(req.getHeight())
                .weight(req.getWeight())
                .userChoiceLevel(req.getUserChoiceLevel())
                .activity(req.getActivity())
                .userChoiceGoal(req.getUserChoiceGoal())
                .secret(hash.createHash(req.getPassword()))
                .build();
        System.out.println(req);
        User savedUser = repo.save(toSaveUser);

        Program toSaveProgram =
                Program.builder()
                        .programTitle(req.getProgramTitle())
                        .build();
//        원래 프로그램ID값을 반환하는거였는ㄷ Name으로 바꿈 id값으로 바꿔서 키값으로 조회해서 객체로 반환하게 한다면?
        String programName = programRepo.save(toSaveProgram).getProgramTitle();
        savedUser.setProgramName(programName);  //user필드에 programName을 할당함
        repo.save(savedUser);
        return savedUser.getId();
    }
}

