import bean.SpringBean;
import config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppInitializer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();
        System.out.println("Spring application context initialized with AppConfig");

        SpringBean bean1 = context.getBean(SpringBean.class);
        bean1.testMethod();

        SpringBean bean2 = context.getBean(SpringBean.class);
        bean1.testMethod();

        System.out.println("Bean ID :"+ bean1);
        System.out.println("Bean ID :"+ bean2); // singleton behaviour
    }
}
