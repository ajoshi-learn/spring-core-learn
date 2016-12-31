package app.example7.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by ajoshi on 31-Dec-16.
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TestBean {
    private @NonNull int counter;
    private @NonNull String text;

    public void process() {
        for (int i = 0; i < counter; i++) {
            System.out.println(text);
        }
    }
}
