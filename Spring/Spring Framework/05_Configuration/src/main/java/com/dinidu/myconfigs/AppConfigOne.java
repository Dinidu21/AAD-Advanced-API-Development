package com.dinidu.myconfigs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigOne {
    @Bean
    public com.dinidu.mybeans.A a() {
        return new com.dinidu.mybeans.A();
    }
    @Bean
    public com.dinidu.mybeans.B b() {
        return new com.dinidu.mybeans.B();
    }
}
