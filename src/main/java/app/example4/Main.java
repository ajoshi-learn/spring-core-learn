package app.example4;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ajoshi on 14-Dec-16.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("example4.xml");
//        ConfigurableBeanFactory autowireCapableBeanFactory = (ConfigurableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//        autowireCapableBeanFactory.registerScope("odd-scope", new EveryOddBeanNewScope());
        applicationContext.getBean("beanExample", BeanExample.class);
        applicationContext.getBean("beanExample", BeanExample.class);
        applicationContext.getBean("beanExample", BeanExample.class);
        applicationContext.getBean("beanExample", BeanExample.class);
        applicationContext.getBean("beanExample", BeanExample.class);
        applicationContext.getBean("beanExample", BeanExample.class);
        applicationContext.getBean("beanExample", BeanExample.class);
        applicationContext.getBean("beanExample", BeanExample.class);
    }
}
