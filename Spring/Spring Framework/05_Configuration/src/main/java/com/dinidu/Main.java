package com.dinidu;

import com.dinidu.bean.SpringBeanOne;
import com.dinidu.config.AppConfig2;
import com.dinidu.config.AppConfig1;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        //Multiple configuration classes
        context.register(AppConfig1.class);
        context.register(AppConfig2.class);
        context.refresh();
        SpringBeanOne springBeanOne = context.getBean(SpringBeanOne.class);
        System.out.println(springBeanOne);
        SpringBeanOne springBeanTwo = context.getBean(SpringBeanOne.class);
        System.out.println(springBeanTwo);

        context.registerShutdownHook();
    }
}