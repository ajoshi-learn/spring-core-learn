package app.example4;

import org.springframework.beans.factory.InitializingBean;

/**
 * Created by ajoshi on 14-Dec-16.
 */
public class BeanExample implements InitializingBean{

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("post construct");
    }
}
