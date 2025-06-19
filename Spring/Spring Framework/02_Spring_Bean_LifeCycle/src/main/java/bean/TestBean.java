package src.main.java.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class TestBean {
    public TestBean() {
        System.out.println("TestBean constructor called");
    }
}
