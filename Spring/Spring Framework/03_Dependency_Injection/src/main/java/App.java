import config.AppConfig;
import di.Bean2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(AppConfig.class);
            context.refresh();

            Bean2 bean =context.getBean(Bean2.class);
            bean.test();
            context.registerShutdownHook();
    }
}
