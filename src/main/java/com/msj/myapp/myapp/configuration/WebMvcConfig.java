package com.msj.myapp.myapp.configuration;

import com.msj.myapp.myapp.myCoach.Authinterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private Authinterceptor authinterceptor;

    // CORS(cross orgin resource sharing)
    // 다른 origin끼리 자원을 공유할 수 있게 하는 것
    // 기본으로 웹(js)에서는 CORS가 안 됨.

    // origin의 구성요소는 프로토콜+주소(도메인,IP)+포트
    // http:// + 127.0.0.1 + :5500
    // http://localhost:8080

    // 서버쪽에서 접근 가능한 orgin 목록, 경로목록, 메서드 목록을 설정해주어야함.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**") // 모든 경로에 대해
                .allowedOrigins(
                        "http://localhost:5500",
                        "http://127.0.0.1:5500") // 로컬 호스트 origin 허용
                .allowedMethods("*"); // 모든 메서드 허용(GET, POST.....)
    }

//    인증처리용 인터셉터를 추가
    @Override  //인터셉터 오버라이드 +등록이 되어 있어야 함+
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authinterceptor);
    }
}