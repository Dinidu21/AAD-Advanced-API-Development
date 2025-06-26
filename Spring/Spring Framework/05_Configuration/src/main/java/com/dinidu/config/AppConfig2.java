package com.dinidu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AppConfig2 {
    @Bean
    public com.dinidu.bean.SpringBeanTwo springBeanTwo() {
        return new com.dinidu.bean.SpringBeanTwo();
    }
}
