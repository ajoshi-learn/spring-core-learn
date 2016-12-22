package app.example6;

import app.example6.bpp.SimpleBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ajoshi on 22-Dec-16.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("example6.xml");
        SimpleBean simpleBean = applicationContext.getBean("simpleBean", SimpleBean.class);
        System.out.println(simpleBean.getData());
    }
}
