package com.msj.myapp.auth.util;

//gradle 외부라이브러리 추가및 동기화
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.msj.myapp.auth.AuthProfile;

import java.util.Date;

public class JWT {

    public String secret = "your-secret";

    public final int TOKEN_TIMEOUT = 1000 * 60 * 60 * 24* 7;

//  토큰생성
    public String createToken(long id){
        Date now = new Date();
        Date exp = new Date(now.getTime() + TOKEN_TIMEOUT);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return com.auth0.jwt.JWT.create()
                .withSubject(String.valueOf(id))
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(algorithm);
    }


//  토큰 검증
    public AuthProfile validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 검증 객체 생성
        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm).build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            Long id = Long.valueOf(decodedJWT.getSubject());
            return AuthProfile.builder()
                    .id(id)
                    .build();

        } catch (JWTVerificationException e) {
            return null;
        }
    }
}