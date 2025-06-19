package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("bean") // Scans the 'bean' package for Spring components
@Configuration
public class AppConfig {

}
