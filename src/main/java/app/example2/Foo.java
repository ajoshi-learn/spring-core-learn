package app.example2;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by ajoshi on 10-Nov-16.
 */
@RequiredArgsConstructor
@Data
public class Foo {
    private @NonNull Baz baz;
    private @NonNull Bar bar;
    private @NonNull int number;
}
