package app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ajoshi on 11-Nov-16.
 */
public interface Constants {
    String APPLICATION_CONTEXT_LOCATION = "application-context.xml";

    default ApplicationContext getApplicationContext() {
        return new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_LOCATION);
    }
}
