package bean;

import org.springframework.stereotype.Component;

@Component
public class SpringBean {
    public SpringBean() {
        System.out.println("SpringBean instance created");
    }

    public void testMethod() {
        System.out.println("Test method called on SpringBean");
    }
}
