package com.dinidu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dinidu.bean")
public class AppConfig {

    @Bean
    public com.dinidu.bean.SpringBean1 springBean1() {
        return new com.dinidu.bean.SpringBean1();
    }

    @Bean
    public com.dinidu.bean.SpringBean2 springBean2() {
        return new com.dinidu.bean.SpringBean2();
    }

}
