package di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Bean2 implements DIInterface {

    //property injection

    DI di;

   /* @Autowired
    public Test2(DI di) {
        this.di = di;
    }*/
    /*@Autowired
    public void setterMethod(DI di) {
        this.di = di;
    }*/

    public void test(){
        di.sayHello();
    }

    @Autowired
    @Override
    public void injected(DI test1) {
        this.di = test1;
    }
}
