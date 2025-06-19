package bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Boy {
    @Qualifier("girl1")
    @Autowired
    Agreement girl;

    public Boy (){
        System.out.println("Boy Instance Created");
    }

    public void chatWithBoyFriend() {
        girl.chat();
    }
}
