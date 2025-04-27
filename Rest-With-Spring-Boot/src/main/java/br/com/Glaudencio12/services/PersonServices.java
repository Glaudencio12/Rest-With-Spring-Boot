package br.com.Glaudencio12.services;

import br.com.Glaudencio12.exception.ResourceNotFoundException;
import br.com.Glaudencio12.model.Person;
import br.com.Glaudencio12.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonServices {
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());
    @Autowired
    PersonRepository repository;

    public Person Create(Person person){
        logger.info("Create one Persons!");
        return repository.save(person);
    }

    public List<Person> findAll(){
        logger.info("Find all Peoples!");
        return repository.findAll();
    }

    public Person findByiD(Long id){
        logger.info("Find one Person!");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
    }

    public Person updatePerson(Person person){
        logger.info("Updating one person!");
        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        return repository.save(person);
    }

    public void delete(Long id){
        logger.info("Deleting one person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        repository.delete(entity);
    }
}
