package app.example1;

import app.ApplicationContextFactory;
import app.Constants;
import org.springframework.context.ApplicationContext;

/**
 * Created by ajoshi on 10-Nov-16.
 */
public class Main implements Constants {
    public static void main(String[] args) {
        ApplicationContext context = ApplicationContextFactory.getApplicationContextFactory().getApplicationContext();
        Pet pet = context.getBean("pet", Pet.class);
        System.out.println(pet.getName());

        StaticPet staticPet = context.getBean("staticPet", StaticPet.class);
        Pet petFromFactory = context.getBean("petFromFactory", Pet.class);
        System.out.println(petFromFactory.getName());
    }
}
