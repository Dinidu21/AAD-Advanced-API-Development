package bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton") // Default scope is singleton, but explicitly defining it
public class Boy {
    @Autowired
    Agreement agreement;

    public Boy (){
        System.out.println("Boy Instance Created");
    }

    public void chatWithBoyFriend() {
        agreement.chat();
    }
}
