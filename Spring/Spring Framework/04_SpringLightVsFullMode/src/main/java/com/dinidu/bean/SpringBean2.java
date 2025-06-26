package com.dinidu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBean2 implements InitializingBean, DisposableBean , BeanFactoryAware , ApplicationContextAware {
    public SpringBean2() {
        System.out.println("SpringBean2 constructor called");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("SpringBean2 setBeanFactory called");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("SpringBean2 destroy called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("SpringBean2 afterPropertiesSet called");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("SpringBean2 setApplicationContext called");
    }
}
