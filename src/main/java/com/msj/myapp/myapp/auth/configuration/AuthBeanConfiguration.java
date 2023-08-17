package com.msj.myapp.myapp.auth.configuration;


import com.msj.myapp.myapp.auth.util.Hash;
import com.msj.myapp.myapp.auth.util.JWT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthBeanConfiguration {

    @Bean
    public Hash Hash() {
        return new Hash();
    }

    @Bean
    public JWT jwt(){
        return new JWT();
    }
}
