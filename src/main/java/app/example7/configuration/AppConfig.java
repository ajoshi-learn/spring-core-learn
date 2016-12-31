package app.example7.configuration;

import app.example7.beans.Client;
import app.example7.beans.MyService;
import app.example7.beans.MyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by arturjoshi on 26-Dec-16.
 */
@Configuration
@ComponentScan(basePackages = "app.example7")
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }

    @Bean
    public Client client() {
        return new Client();
    }
}
