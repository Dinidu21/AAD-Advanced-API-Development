import bean.MyBean;
import config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        MyBean bean = context.getBean(MyBean.class);
        System.out.println("Bean retrieved: " + bean);

        // Register shutdown hook to ensure proper cleanup
        context.registerShutdownHook();
    }
}
