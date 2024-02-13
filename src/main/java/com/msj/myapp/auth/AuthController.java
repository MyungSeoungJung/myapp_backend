package com.msj.myapp.auth;



import com.msj.myapp.auth.util.Hash;
import com.msj.myapp.auth.util.JWT;
import com.msj.myapp.program.ProgramRepository;
import com.msj.myapp.user.User;
import com.msj.myapp.user.UserRepository;
import com.msj.myapp.user.request.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "유저 관리 처리")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private Hash hash;

    @Autowired
    private JWT jwt;


    @Operation(summary = "회원가입")
    @PostMapping (value = "/signup")
    public ResponseEntity signup (@RequestBody SignupRequest req){
        long userId = authService.createIdentity(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @Operation(summary = "로그인")
    @PostMapping (value = "/signin")
    public ResponseEntity signIn (@RequestParam String phone,
                                  @RequestParam String password,
                                  HttpServletResponse res){
        Optional<User> findUser = userRepository.findByPhone(phone);  //휴대폰 번호로 일치하는 유저 객체 찾기
        if (!findUser.isPresent()){  //유저 없으면
            // 유저 못 찾으면 401 에러
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "존재하지 않는 유저입니다."); // 에러 메시지 추가
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        }
        // 사용자가 입력한 패스워드를 해시화하여 데이터베이스에 저장된 해시와 비교
       boolean isVerified = hash.verifyHash(password,findUser.get().getSecret());

        if(!isVerified) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = findUser.get();
        System.out.println("유저" + user);


//      토큰생성
        String token = jwt.createToken(
                user.getId());
        System.out.println("토큰" + token);

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge((int) (jwt.TOKEN_TIMEOUT / 1000L)); // 만료시간
        cookie.setDomain("localhost"); // 쿠키를 사용할 수 있는 도메인

        // 응답헤더에 쿠키 추가
        res.addCookie(cookie);
        System.out.println(cookie);
        
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(ServletUriComponentsBuilder
                        .fromHttpUrl("http://localhost:5500/main_page/main.html")  //리다이렉트
                        .build().toUri())
                .build();

    }

}
