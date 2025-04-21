package br.com.Glaudencio12.controllers;

import br.com.Glaudencio12.PersonServices;
import br.com.Glaudencio12.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonServices services;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(@PathVariable("id") String id){
        return services.findByiD(id);
    }

    @GetMapping(value = "/allPersons", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll(){
        return services.findAll();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody Person person){
        return services.Create(person);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person){
        return services.updatePerson(person);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id){
        services.delete(id);
    }
}
