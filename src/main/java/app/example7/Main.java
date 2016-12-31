package app.example7;

import app.example7.beans.TestBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by ajoshi on 31-Dec-16.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.getBean("testBean", TestBean.class).process();
    }
}
