package com.msj.myapp.myapp.myCoach.MyCoachutil;

//gradle 외부라이브러리 추가및 동기화
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.msj.myapp.myapp.myCoach.AuthProfile;

import java.util.Date;

public class MyAppJWT {

    public String secret = "your-secret";

    public final int TOKEN_TIMEOUT = 1000 * 60 * 60 * 24* 7;
//  ------------------------------------------토큰생성
    public String createToken(long id,String name,String sex,int weight,int height,int age,double Activity,
                              int GoalCal,String ProgramName,String userChoiceLevel,String userChoiceGoal){
        Date now = new Date();
        Date exp = new Date(now.getTime() + TOKEN_TIMEOUT);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //AuthProfile의 필드값하고 key값이 동일해야함  .withClaim("key",value)
        return com.auth0.jwt.JWT.create()
                .withSubject(String.valueOf(id))
                .withClaim("name",name)
                .withClaim("sex",sex)
                .withClaim("weight",weight)
                .withClaim("height",height)
                .withClaim("age",age)
                .withClaim("activity",Activity)
                .withClaim("goalCal",GoalCal)
                .withClaim("programName",ProgramName)
                .withClaim("userChoiceLevel",userChoiceLevel)
                .withClaim("userChoiceGoal",userChoiceGoal)
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(algorithm);
    }


//   ----------------------------------------토큰 decoded
    public AuthProfile validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 검증 객체 생성
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            // 토큰 검증 제대로 된 상황
            // 토큰 페이로드(데이터, subject/claim)를 조회
            Long id = Long.valueOf(decodedJWT.getSubject());
            String name = decodedJWT
                    .getClaim("name").asString();
            String sex = decodedJWT
                    .getClaim("sex").asString();
            int weight = decodedJWT.getClaim("weight").asInt();
            int height = decodedJWT.getClaim("height").asInt();
            int age = decodedJWT.getClaim("age").asInt();
            double activity = decodedJWT.getClaim("activity").asDouble();
            int goalCal = decodedJWT.getClaim("goalCal").asInt();
            String programName =decodedJWT
                    .getClaim("programName").asString();
            String userChoiceLevel = decodedJWT
                    .getClaim("userChoiceLevel").asString();
            String userChoiceGoal = decodedJWT
                    .getClaim("userChoiceGoal").asString();

            return AuthProfile.builder()
                    .id(id)
                    .name(name)
                    .sex(sex)
                    .weight(weight)
                    .height(height)
                    .age(age)
                    .activity(activity)
                    .goalCal(goalCal)
                    .programName(programName)
                    .userChoiceGoal(userChoiceGoal)
                    .userChoiceLevel(userChoiceLevel)
                    .build();

        } catch (JWTVerificationException e) {
            // 토큰 검증 오류 상황
            return null;
        }
    }
}