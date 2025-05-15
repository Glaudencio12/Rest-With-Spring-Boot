package br.com.Glaudencio12.services;

import br.com.Glaudencio12.dto.PersonDTO;
import br.com.Glaudencio12.exception.ResourceNotFoundException;
import br.com.Glaudencio12.mapper.ObjectMapper;
import br.com.Glaudencio12.model.Person;
import br.com.Glaudencio12.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PersonServices {
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());
    @Autowired
    PersonRepository repository;

    public PersonDTO create(PersonDTO person){
        logger.info("Create one Persons!");
        var entity = ObjectMapper.parseObject(person, Person.class);
        return ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
    }

    public List<PersonDTO> findAll(){
        logger.info("Find all Peoples!");
        return ObjectMapper.parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findByiD(Long id){
        logger.info("Find one Person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        return ObjectMapper.parseObject(entity, PersonDTO.class);
    }


    public PersonDTO updatePersonDTO(PersonDTO person){
        logger.info("Updating one person!");
        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
    }

    public void delete(Long id){
        logger.info("Deleting one person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        repository.delete(entity);
    }
}
