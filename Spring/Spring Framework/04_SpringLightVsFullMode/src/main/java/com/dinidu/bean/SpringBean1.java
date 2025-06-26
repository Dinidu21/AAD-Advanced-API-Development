package com.dinidu.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBean1 implements InitializingBean , BeanFactoryAware, DisposableBean , ApplicationContextAware {
    public SpringBean1() {
        System.out.println("SpringBean1 constructor called");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("SpringBean1 setBeanFactory called");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("SpringBean1 destroy called");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("SpringBean1 afterPropertiesSet called");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("SpringBean1 setApplicationContext called");
    }
}
