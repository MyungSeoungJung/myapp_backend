package com.msj.myapp.auth;

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
private long id;
private String name;
private String sex;
private int weight;
private int height;
private int age;
private double amountOfActivity;
private int targetCalories;
private String exerciseProgramName;
private String levelOfExercise;
private String purposeOfExercise;
}
