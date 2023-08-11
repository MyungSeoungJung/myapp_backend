package com.msj.myapp.myapp.myCoach.UserConfiguration;


import com.msj.myapp.myapp.myCoach.MyCoachutil.Hash;
import com.msj.myapp.myapp.myCoach.MyCoachutil.JWT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {

    @Bean
    public Hash Hash() {
        return new Hash();
    }

    @Bean
    public JWT jwt(){
        return new JWT();
    }
}
