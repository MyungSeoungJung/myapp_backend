package com.msj.myapp.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  // Policy = 정책, 즉 런타임중에도 어노테이션 정보가 유지되게끔 정책을 정한다.
@Target({ElementType.METHOD}) // 메소드에만 적용할 수 있도록 지정
public @interface Auth {
    // 역할(일반사용자, 골드사용자, 관리자, 판매관리자)..
    // @Auth(role="GOLD")
//    public String role();
    public boolean require() default true;  //인증이 필요한지 true/false 가리기
}