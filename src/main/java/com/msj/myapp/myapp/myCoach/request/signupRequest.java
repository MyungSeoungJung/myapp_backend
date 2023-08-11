package com.msj.myapp.myapp.myCoach.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class signupRequest {
//    HTML에서 받은 회원가입 양식
    //JS에서 보내는 필드타입과 이름이 일치해야함
//    -------user
private String name;
    private int age;
    private String sex;
    private String phone;
    private int height;
    private int weight;
    private String userChoiceLevel;   //초급자 중급자 //상급자
    private Double activity; // 활동량
    private String userChoiceGoal;    //다이어트 //근비대
    private String password;

//    --------program
    private String ProgramTitle;

}
