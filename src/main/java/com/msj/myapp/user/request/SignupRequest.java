package com.msj.myapp.user.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequest {
//    HTML에서 받은 회원가입 양식
    //JS에서 보내는 필드타입과 이름이 일치해야함
//    -------user
private String name;
    private int age;
    private String sex;
    private String phone;
    private int height;
    private int weight;
    private int goalCal;
    private String userChoiceLevel;
    private Double activity; // 활동량
    private String userChoiceGoal;
    private String password;
    private String programTitle;

}
