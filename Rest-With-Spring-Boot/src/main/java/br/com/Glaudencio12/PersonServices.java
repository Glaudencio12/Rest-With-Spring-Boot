package br.com.Glaudencio12;

import br.com.Glaudencio12.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {
    private final AtomicLong counter = new AtomicLong();
    private final Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<Person> findAll(){
        logger.info("Find all Peoples!");        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    public Person findByiD(String id){
        logger.info("Find one Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Glaudencio");
        person.setLastName("Costa");
        person.setAddress("Coelho Neto - Bom Sucesso");
        person.setGender("Male");
        return person;
    }

    public Person Create(Person person){
        logger.info("Create one Persons!");
        return person;
    }

    public Person updatePerson(Person person){
        logger.info("Updating one person!");
        return person;
    }

    public void delete(String id){
        logger.info("Deleting one person!");
    }

    private Person mockPerson(int i) {
        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFirstName("Firstname: " + i);
        person.setLastName("Lastname: " + i);
        person.setAddress("Some Address in Brazil");
        person.setGender((i % 2 == 0 ? "Male" : "Feminine"));
        return person;
    }
}
