package com.dinidu.config;

import com.dinidu.bean.SpringBean1;
import com.dinidu.bean.SpringBean2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.dinidu.bean")
public class AppConfig {

    @Bean
    public com.dinidu.bean.SpringBean1 springBean1() {
        SpringBean2 springBean2 = springBean2();
        SpringBean2 springBean21 = springBean2();
        System.out.println(springBean2);
        System.out.println(springBean21);

        return new com.dinidu.bean.SpringBean1();
    }

    @Bean
    public com.dinidu.bean.SpringBean2 springBean2() {
        return new com.dinidu.bean.SpringBean2();
    }
}
