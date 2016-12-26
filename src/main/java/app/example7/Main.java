package app.example7;

import app.example7.beans.Client;
import app.example7.configuration.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by arturjoshi on 26-Dec-16.
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig.class);
        applicationContext.refresh();
        Client client = applicationContext.getBean("client", Client.class);
        client.getMyService().doSmth();
    }
}
