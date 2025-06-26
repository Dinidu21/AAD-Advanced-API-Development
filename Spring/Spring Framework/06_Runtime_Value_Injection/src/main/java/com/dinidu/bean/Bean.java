package com.dinidu.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Bean {

    public Bean(
            @Value("Dinidu") String name,
            @Value("#{systemProperties['os.name']}") String osName
    ) {
        System.out.println(
                "Bean constructor called with name: " + name + " \nand OS: " + osName
        );
    }

    @Autowired
    public Bean(
            @Value("Dinidu") String name,
            @Value("#{systemProperties['os.name']}") String osName,
            @Value("#{systemProperties['java.version']}") String javaVersion
    ) {
        System.out.println(
                "Bean constructor called with name: \n" + name + " \nand OS: " + osName + "\nand Java Version: " + javaVersion
        );
    }

}
