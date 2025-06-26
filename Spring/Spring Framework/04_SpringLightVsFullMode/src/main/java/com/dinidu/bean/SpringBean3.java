package com.dinidu.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SpringBean3 {
    public SpringBean3() {
        System.out.println("SpringBean3 constructor called");
    }

    // Interbean dependency satisfaction is not in Spring Light Mode
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
