package config;

import bean.SpringBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan (basePackages = "bean")
@ComponentScan (basePackageClasses = SpringBean.class)
public class AppConfig {

}
