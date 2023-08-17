package com.msj.myapp.myapp.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthProfile {
//      user.setId(Long.parseLong(userId));
//            user.setName(userName);
//            user.setPhone(phone);
//            user.setUserChoiceLevel(userChoiceLevel);
//            user.setUserChoiceGoal(userChoiceGoal);

private long id;
private String name;
private String sex;
private int weight;
private int height;
private int age;
private double activity;
private int goalCal;
private String programName;
private String userChoiceLevel;
private String userChoiceGoal;
}
