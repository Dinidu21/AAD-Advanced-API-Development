package com.dinidu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig1 {
    @Bean
    public com.dinidu.bean.SpringBeanOne springBeanOne() {
        return new com.dinidu.bean.SpringBeanOne();
    }
}
