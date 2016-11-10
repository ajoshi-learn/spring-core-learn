package app.example1.petfactory;

import app.example1.Pet;

/**
 * Created by ajoshi on 10-Nov-16.
 */
public class PetFactory {

    private PetFactory() {
    }

    public Pet getDefaultPet() {
        return new Pet("default");
    }
}
