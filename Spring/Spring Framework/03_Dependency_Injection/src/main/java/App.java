import bean.Boy;
import config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.register(AppConfig.class);
            context.refresh();

            Boy boy = context.getBean(Boy.class);
            boy.chatWithBoyFriend();
            context.registerShutdownHook();
    }
}
