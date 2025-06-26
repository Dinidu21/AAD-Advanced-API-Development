package com.dinidu.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Bean {
    // When using @Autowired, the constructor will be called automatically by Spring
    // when there is 2 Autowired constructors, Spring will throw an exception
    // because it cannot decide which constructor to use.
    // To avoid this, we can use @Autowired(required = false) to make the constructor optional.
    // if  we use more than one constructor with @Autowired with (required = false) then it will
    // call the highest paras constructor it finds.

    @Autowired(required = false)
    public Bean(
            @Value("Dinidu") String name,
            @Value("#{systemProperties['os.name']}") String osName
    ) {
        System.out.println(
                "Bean constructor called with name: " + name + " \nand OS: " + osName
        );
    }

    @Autowired(required = false)
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
