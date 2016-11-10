package app.example1;

/**
 * Created by ajoshi on 10-Nov-16.
 */
public class StaticPet {
    private static StaticPet instance = new StaticPet();

    private StaticPet() {
    }

    public static StaticPet getInstance() {
        System.out.println("STATIC");
        return instance;
    }
}
