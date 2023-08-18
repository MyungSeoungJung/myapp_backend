package com.msj.myapp.auth.configuration;


import com.msj.myapp.auth.util.JWT;
import com.msj.myapp.auth.util.Hash;
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
