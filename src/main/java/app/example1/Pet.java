package app.example1;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by ajoshi on 10-Nov-16.
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Pet {
    private @NonNull String name;
}
