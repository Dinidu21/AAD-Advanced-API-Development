package com.dinidu.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = {"com.dinidu.bean", "com.dinidu.controller"})
public class WebAppConfig implements WebMvcConfigurer {

}
