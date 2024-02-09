package com.msj.myapp.user;

import com.msj.myapp.auth.Auth;
import com.msj.myapp.auth.AuthProfile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Operation(summary = "유저 이름 띄우기", security = { @SecurityRequirement(name = "bearer-key") })
    @Auth
    @GetMapping(value = "/displayUserName")
    public ResponseEntity<Map<String,Object>> mainPage (@RequestAttribute AuthProfile authProfile){
        Map<String, Object> response = new HashMap<>();
        Optional<User> UserOptional = userRepository.findById(authProfile.getId());
        if(UserOptional.isPresent()){
            User user = UserOptional.get();
            response.put("name",user.getName());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Auth
    @GetMapping("/userInfoResponse")
    public ResponseEntity<Map<String,Object>> userInfoResponse(@RequestAttribute AuthProfile authProfile){
        Optional<User> user = userRepository.findById(authProfile.getId());
        Map<String, Object> res = new HashMap<>();
        res.put("sex",user.get().getSex());
        res.put("age",user.get().getAge());
        res.put("height",user.get().getHeight());
        res.put("weight",user.get().getWeight());
        res.put("activity",user.get().getActivity());
        res.put("goalCal",user.get().getGoalCal());
        res.put("userChoiceGoal",user.get().getUserChoiceGoal());
        System.out.println(user.get().getSex() );
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
