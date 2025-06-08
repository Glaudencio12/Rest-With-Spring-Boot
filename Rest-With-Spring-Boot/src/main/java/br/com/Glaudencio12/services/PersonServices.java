package br.com.Glaudencio12.services;

import br.com.Glaudencio12.controllers.PersonController;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class PersonServices {
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());
    @Autowired
    PersonRepository repository;

    private void hateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET").withTitle("Finding one person"));
        dto.add(linkTo(methodOn(PersonController.class).findAll()).withRel("FindAll").withType("GET").withTitle("Find All persons"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("Create").withType("POST").withTitle("Create a person"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("Update").withType("PUT").withTitle("Updating a person"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("Delete").withType("DELETE").withTitle("Deleting a person"));
    }

    public PersonDTO create(PersonDTO person){
        logger.info("Create one Persons!");
        var entity = ObjectMapper.parseObject(person, Person.class);
        var dto = ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
        hateoasLinks(dto);
        return dto;
    }

    public List<PersonDTO> findAll(){
        logger.info("Find all Peoples!");
        var dtos = ObjectMapper.parseListObjects(repository.findAll(), PersonDTO.class);
        dtos.forEach(p -> hateoasLinks(p));
        return dtos;
    }

    public PersonDTO findByiD(Long id){
        logger.info("Find one Person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        var dto = ObjectMapper.parseObject(entity, PersonDTO.class);
        hateoasLinks(dto);
        return dto;
    }

    public PersonDTO updatePersonDTO(PersonDTO person){
        logger.info("Updating one person!");
        Person entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var dto = ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
        hateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        repository.delete(entity);
    }
}
