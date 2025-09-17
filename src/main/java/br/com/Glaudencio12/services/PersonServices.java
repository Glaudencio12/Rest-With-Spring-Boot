package br.com.Glaudencio12.services;

import br.com.Glaudencio12.controllers.PersonController;
import br.com.Glaudencio12.dto.PersonDTO;
import br.com.Glaudencio12.exception.RequireObjectIsNullException;
import br.com.Glaudencio12.exception.ResourceNotFoundException;
import br.com.Glaudencio12.mapper.ObjectMapper;
import br.com.Glaudencio12.model.Person;
import br.com.Glaudencio12.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class PersonServices {
    private final Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());
    @Autowired
    PersonRepository repository;

    private void hateoasLinks(PersonDTO dto) {
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET").withTitle("Finding one person"));
        dto.add(linkTo(methodOn(PersonController.class).findAll(1, 12, "asc")).withRel("FindAll").withType("GET").withTitle("Find All persons"));
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("Create").withType("POST").withTitle("Create a person"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("Update").withType("PUT").withTitle("Updating a person"));
        dto.add(linkTo(methodOn(PersonController.class).enablePerson(dto.getId())).withRel("Patch").withType("PATCH").withTitle("Disables the status of the person"));
        dto.add(linkTo(methodOn(PersonController.class).patchPersonDynamic(dto.getId(), null)).withRel("Patch").withType("PATCH").withTitle("Partially updates a person"));
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("Delete").withType("DELETE").withTitle("Deleting a person"));
    }

    public PersonDTO create(PersonDTO person){
        if (person == null) {
            throw new RequireObjectIsNullException();
        }
        logger.info("Create one Person!");
        var entity = ObjectMapper.parseObject(person, Person.class);
        var dto = ObjectMapper.parseObject(repository.save(entity), PersonDTO.class);
        hateoasLinks(dto);
        return dto;
    }

    public Page<PersonDTO> findAll(Pageable pageable){
        logger.info("Find all Peoples!");
        var persons = repository.findAll(pageable);
        return persons.map(person -> {
            var dto = ObjectMapper.parseObject(person, PersonDTO.class);
            hateoasLinks(dto);
            return dto;
        });
    }

    public PersonDTO findByiD(Long id){
        logger.info("Find one Person!");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        var dto = ObjectMapper.parseObject(entity, PersonDTO.class);
        hateoasLinks(dto);
        return dto;
    }

    public PersonDTO updatePersonDTO(PersonDTO person){
        if (person == null) {
            throw new RequireObjectIsNullException();
        }
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

    @Transactional
    public PersonDTO enablePerson(Long id){
        logger.info("Enabling one person!");
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        repository.enabledPerson(id);
        var entity = repository.findById(id).get();
        var dto = ObjectMapper.parseObject(entity, PersonDTO.class);
        hateoasLinks(dto);
        return dto;
    }

    public PersonDTO patchPersonDynamic(Long id, Map<String, Object> campos){
        //FALTA REALIZAR AS VALIDAÇÕES DOS DADOS
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        campos.forEach((campo, valor) -> {
            Field field = ReflectionUtils.findField(Person.class, campo);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, entity, valor);
            }
        });
        var saved = repository.save(entity);
        var dto = ObjectMapper.parseObject(saved, PersonDTO.class);
        hateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting one person!");
        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not records found for this id"));
        repository.delete(entity);
    }
}
