import bean.MyConnection;
import bean.TestBean;
import config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        TestBean testBean = context.getBean(TestBean.class);
        System.out.println("TestBean instance: " + testBean);
        MyConnection myConnection = context.getBean(MyConnection.class);
        System.out.println("MyConnection instance: "+ myConnection);
        context.registerShutdownHook();
    }
}
