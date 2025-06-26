package com.dinidu.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Bean {
    public Bean(
            @Value("Dinidu") String name,
            @Value("#{systemProperties['os.name']}") String osName,
            @Value("#{systemEnvironment['JAVA_HOME']}") String javaHome,
            @Value("#{systemEnvironment['PATH']}") String path,
            @Value("#{systemProperties['user.timezone']}") String timezone,
            @Value("#{systemProperties['user.language']}") String language,
            @Value("#{systemProperties['user.country']}") String country,
            @Value("#{systemProperties['user.dir']}") String userDir,
            @Value("#{systemProperties['user.name']}") String userName

    ) {
        System.out.println("Bean constructor called " + name +
                " with OS: " + osName +
                ", JAVA_HOME: " + javaHome +
                ", JAVA Path: " + path +
                ", Timezone: " + timezone +
                ", Language: " + language +
                ", Country: " + country +
                ", User Directory: " + userDir +
                ", User Name: " + userName
        );
    }
}
