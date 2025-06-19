package bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton") // Default scope is singleton, but explicitly defining it
public class Girl implements Agreement {

    public Girl(){
        System.out.println("Girl Instance Created");
    }

    @Override
    public void chat() {
        System.out.println("Chatting with girlfriend");
    }
}
