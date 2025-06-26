package com.dinidu.myconfigs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigTwo {
    @Bean
    public com.dinidu.mybeans.C c() {
        return new com.dinidu.mybeans.C();
    }
    @Bean
    public com.dinidu.mybeans.D d() {
        return new com.dinidu.mybeans.D();
    }
}
