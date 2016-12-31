package app.example7;

import app.example7.beans.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by ajoshi on 31-Dec-16.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "app.example7")
public class AppConfig {
    @Bean
    public TestBean testBean() {
        return new TestBean(5, "hello");
    }
}
