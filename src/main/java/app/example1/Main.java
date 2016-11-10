package app.example1;

import app.example1.Pet;
import app.example1.StaticPet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by ajoshi on 10-Nov-16.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        Pet pet = context.getBean("pet", Pet.class);
        System.out.println(pet.getName());

        StaticPet staticPet = context.getBean("staticPet", StaticPet.class);
        Pet petFromFactory = context.getBean("petFromFactory", Pet.class);
        System.out.println(petFromFactory.getName());
    }
}
