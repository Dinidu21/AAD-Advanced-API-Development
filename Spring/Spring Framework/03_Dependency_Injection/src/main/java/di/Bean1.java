package di;

import org.springframework.stereotype.Component;

@Component
public class Bean1 implements DI {
    @Override
    public void sayHello() {
        System.out.println("Bean1() Say Hello");
    }
}