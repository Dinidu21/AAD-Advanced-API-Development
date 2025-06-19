package bean;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary // This annotation indicates that this bean should be given preference when multiple beans of the same type are present
public class Girl1 implements Agreement {

    public Girl1(){
        System.out.println("Girl Instance Created");
    }

    @Override
    public void chat() {
        System.out.println("Chatting with girlfriend");
    }
}
