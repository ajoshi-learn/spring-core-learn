package app.example7.beans;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by arturjoshi on 26-Dec-16.
 */
@Component
@Data
public class Client {
    @Autowired
    private MyService myService;
}
