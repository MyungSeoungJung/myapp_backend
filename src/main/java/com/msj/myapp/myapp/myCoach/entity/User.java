package com.msj.myapp.myapp.myCoach.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//User필드 그대로 데이터베이스에 table이 생김
public class User {
    //   이름,성별,나이 같은 인적사항 user에 저장
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int age;
    @Column(unique = true)  //pk 키 넣어줘야 함.
    private String phone;
    private int height;
    private int weight;
    private String sex;
    private String userChoiceLevel;   //초급자 중급자 //상급자
    private Double activity; //활동량
    private String userChoiceGoal;    //다이어트 //근비대
    private int goalCal; //목표 칼로리 주 0.25감량 0.5감량 등
    @Column(length = 500)
    private String secret;

    //UserService에서 set받은 programName을 DB에 저장
    private String programName;




}
