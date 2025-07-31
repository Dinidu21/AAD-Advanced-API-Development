package com.dinidu.springsecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    public CommandLineRunner launchBrowser() {
        return args -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI("http://localhost:8080/"));
                } else {
                    System.out.println("Desktop browsing not supported. Please open http://localhost:8080/ manually.");
                }
            } catch (Exception e) {
                System.out.println("Error launching browser: " + e.getMessage());
            }
        };
    }
}