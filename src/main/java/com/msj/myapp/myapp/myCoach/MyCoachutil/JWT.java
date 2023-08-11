package com.msj.myapp.myapp.myCoach.MyCoachutil;

//gradle 외부라이브러리 추가및 동기화
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JWT {

    public String secret = "your-secret";

    public final int TOKEN_TIMEOUT = 1000 * 60 * 60 * 24* 7;

    public String createToken(long id,String name,String phone,String userChoiceLevel,String userChoiceGoal){
        Date now = new Date();
        Date exp = new Date(now.getTime() + TOKEN_TIMEOUT);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return com.auth0.jwt.JWT.create()
                .withSubject(String.valueOf(id))
                .withClaim("userName",name)
                .withClaim("phone",phone)
                .withClaim("userChoiceLevel",userChoiceLevel)
                .withClaim("userChoiceGoal",userChoiceGoal)
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(algorithm);
    }
}
