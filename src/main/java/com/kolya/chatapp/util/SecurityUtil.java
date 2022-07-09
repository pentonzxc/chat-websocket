package com.kolya.chatapp.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    @Bean
    @Scope(scopeName = "singleton")
    public static String USER_PASSWORD(){
        return "password";
    }
}
