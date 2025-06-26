package com.dinidu.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Bean implements InitializingBean {
    @Value("${os.name}")
    private String osName;

    @Value("${DB_NAME}")
    private String dbName;

    public Bean() {
        System.out.println("Bean constructor called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("OS Name: " + osName);
        System.out.println("Database Name: " + dbName);
    }
}
