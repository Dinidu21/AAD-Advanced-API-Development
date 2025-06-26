package com.dinidu.myconfigs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppConfigOne.class, AppConfigTwo.class})
public class AppConfig {
    @Bean
    public com.dinidu.mybeans.E e() {
        return new com.dinidu.mybeans.E();
    }
}
