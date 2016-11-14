package app;

import org.springframework.context.ApplicationContext;

/**
 * Created by ajoshi on 11-Nov-16.
 */
public class ApplicationContextFactory implements Constants {
    private static ApplicationContextFactory INSTANCE = new ApplicationContextFactory();

    private ApplicationContextFactory() {
    }

    public ApplicationContext getApplicationContext() {
        return INSTANCE.getApplicationContext();
    }
    
    public static ApplicationContextFactory getApplicationContextFactory() {
        return INSTANCE;
    }
}
