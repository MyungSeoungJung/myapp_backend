package com.msj.myapp.myapp.myCoach;



import com.msj.myapp.myapp.myCoach.MyCoachutil.Hash;
import com.msj.myapp.myapp.myCoach.MyCoachutil.MyAppJWT;
import com.msj.myapp.myapp.myCoach.entity.CaloricCalculator;
import com.msj.myapp.myapp.myCoach.entity.ProgramRepository;
import com.msj.myapp.myapp.myCoach.entity.User;
import com.msj.myapp.myapp.myCoach.entity.UserRepository;
import com.msj.myapp.myapp.myCoach.request.signupRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private UserService service;
    @Autowired
    private Hash hash;

    @Autowired
    private MyAppJWT myAppJwt;



    @PostMapping (value = "/signup")
    public ResponseEntity signup (@RequestBody signupRequest req){
        System.out.println(req);
        long userId = service.createIdentity(req);

        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @PostMapping (value = "/signin")        //@RequestParam = 쿼리값을 받을때 사용 ex: input값 노션 에러/궁금증목록에 정리
    public ResponseEntity signIn (@RequestParam String phone,
                                  @RequestParam String password,
                                  HttpServletResponse res){

        Optional<User> findUser = repo.findByPhone(phone);  //휴대폰 번호로 일치하는 유저 객체 찾기


        if (!findUser.isPresent()){  //유저 없으면
            // 유저 못 찾으면 401 에러
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // 사용자가 입력한 패스워드를 해시화하여 데이터베이스에 저장된 해시와 비교
       boolean isVerified = hash.verifyHash(password,findUser.get().getSecret());

        if(!isVerified) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = findUser.get();
        System.out.println("유저" + user);

// 로그인 객체 반활할때 user가 선택한 program_name이 null이라면 js에서 알림창 뜨게 만들기
//        ex if(response.program_name == null){
//        alert 이런식으로 }
//        --------------------------------------------토큰생성
        String token = myAppJwt.createToken(
                user.getId(),
                user.getName(),
                user.getWeight(),
                user.getHeight(),
                user.getAge(),
                user.getActivity(),
                user.getGoalCal(),
                user.getProgramName(),
                user.getUserChoiceLevel(),
                user.getUserChoiceGoal());

        System.out.println("토큰" + token);


        //name속성 = token
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge((int) (myAppJwt.TOKEN_TIMEOUT / 1000L)); // 만료시간
        cookie.setDomain("localhost"); // 쿠키를 사용할 수 있 도메인

        // 응답헤더에 쿠키 추가
        res.addCookie(cookie);
        System.out.println(cookie);
        
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(ServletUriComponentsBuilder
                        .fromHttpUrl("http://localhost:5500/mainpage/main.html")   //리다이렉트
                        .build().toUri())
                .build();

    }

    @Auth
    @GetMapping (value = "main")   //Auth어노테이션 작동 토큰 가로채서 @RequestAttribute에 반환
    public ResponseEntity<Map<String,Object>> mainpage (@RequestAttribute AuthProfile authProfile){
        System.out.println("유저 정보" + authProfile);
        //반환할 유저정보
        Map<String, Object> res = new HashMap<>();
        res.put("name",authProfile.getName());
        res.put("weight",authProfile.getWeight());
        res.put("height",authProfile.getHeight());
        res.put("age",authProfile.getAge());
        res.put("activity",authProfile.getActivity());
        res.put("goalCal",authProfile.getGoalCal());
        res.put("programName",authProfile.getProgramName());
        res.put("userChoiceLevel",authProfile.getUserChoiceLevel());
        res.put("userChoiceGoal",authProfile.getUserChoiceGoal());
        System.out.println(authProfile);
//        CaloricCalculator cal = new CaloricCalculator();
//        cal.calculator();
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }



}
