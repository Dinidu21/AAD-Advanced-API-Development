package bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MyConnection implements DisposableBean ,
        BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

    //  The state of Initialization
    public MyConnection() {
        System.out.println("MyConnection constructor called");
    }

    public MyConnection getConnection() {
        System.out.println("MyConnection getConnection method called");
        return this;
    }

    //  context closed and remove the bean from the context
    @Override
    public void destroy() throws Exception {
        System.out.println("MyConnection is being destroyed");
    }

    // Bean Name Aware - > BeanID
    @Override
    public void setBeanName(String name) {
        System.out.println("MyConnection bean name is: " + name);
    }

    // Dependency Injection - > BeanFactory
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("MyConnection bean factory is set");
    }

    // AOP and DP
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("MyConnection application context is set");
    }

    // Complete the initialization of the bean
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("After properties are set");
    }
}
