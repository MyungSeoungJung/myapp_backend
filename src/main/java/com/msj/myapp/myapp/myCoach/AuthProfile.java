package com.msj.myapp.myapp.myCoach;

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
private String phone;
private String userChoiceLevel;
private String userChoiceGoal;
}
