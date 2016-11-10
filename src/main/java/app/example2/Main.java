package app.example2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ajoshi on 10-Nov-16.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Foo foo = context.getBean("foo", Foo.class);
        System.out.println(foo);
    }
}
