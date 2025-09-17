package br.com.Glaudencio12.unitTests.service;

import br.com.Glaudencio12.dto.PersonDTO;
import br.com.Glaudencio12.exception.RequireObjectIsNullException;
import br.com.Glaudencio12.services.PersonServices;
import br.com.Glaudencio12.unitTests.mocks.MockPerson;
import br.com.Glaudencio12.model.Person;
import br.com.Glaudencio12.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {

    MockPerson input;

    @InjectMocks
    private PersonServices service;

    @Mock
    private PersonRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    public void assertLinks(PersonDTO dto, String rel, String href, String type){
        assertTrue(dto.getLinks().stream().anyMatch(link -> link.getRel().value().equals(rel)
                && link.getHref().endsWith(href)
                && link.getType().equals(type)));
    }

    @Test
    void findByiD() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(person));
        var result = service.findByiD(1L);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertLinks(result, "self", "/api/person/v1/1", "GET");
        assertLinks(result, "FindAll", "/api/person/v1", "GET");
        assertLinks(result, "Create", "/api/person/v1", "POST");
        assertLinks(result, "Update", "/api/person/v1", "PUT");
        assertLinks(result, "Delete", "/api/person/v1/1", "DELETE");
    }

    @Test
    void create() {
        Person person = input.mockEntity(1);
        person.setId(1L);
        PersonDTO dto = input.mockDTO(1);
        when(repository.save(ArgumentMatchers.any(Person.class))).thenReturn(person);

        var result = service.create(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getLinks());

        assertLinks(result, "self", "/api/person/v1/1", "GET");
        assertLinks(result, "FindAll", "/api/person/v1", "GET");
        assertLinks(result, "Create", "/api/person/v1", "POST");
        assertLinks(result, "Update", "/api/person/v1", "PUT");
        assertLinks(result, "Delete", "/api/person/v1/1", "DELETE");
    }

    @Test
    void testeCreateWithNullPerson(){
        Exception exception = assertThrows(RequireObjectIsNullException.class, () -> {
           service.create(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void updatePersonDTO() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        PersonDTO dto = input.mockDTO(1);

        when(repository.findById(1L)).thenReturn(Optional.of(person));
        when(repository.save(ArgumentMatchers.any(Person.class))).thenReturn(person);

        var result = service.updatePersonDTO(dto);

        assertLinks(result, "self", "/api/person/v1/1", "GET");
        assertLinks(result, "FindAll", "/api/person/v1", "GET");
        assertLinks(result, "Create", "/api/person/v1", "POST");
        assertLinks(result, "Update", "/api/person/v1", "PUT");
        assertLinks(result, "Delete", "/api/person/v1/1", "DELETE");
    }

    @Test
    void testeUpdateWithNullPerson(){
        Exception exception = assertThrows(RequireObjectIsNullException.class, () -> {
            service.updatePersonDTO(null);
        });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Person person = input.mockEntity(1);
        person.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(person));

        service.delete(person.getId());

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any(Person.class));
    }

    @Test
    @Disabled("Suspnso temporariamente")
    void findAll() {
        List<Person> list = input.mockEntityList();
        when(repository.findAll()).thenReturn(list);
        List<PersonDTO> people =  new ArrayList<>();//service.findAll(1, 2);

        assertNotNull(people);
        assertEquals(14, people.size());

        var personOne = people.get(1);

        assertLinks(personOne, "self", "/api/person/v1/1", "GET");
        assertLinks(personOne, "FindAll", "/api/person/v1", "GET");
        assertLinks(personOne, "Create", "/api/person/v1", "POST");
        assertLinks(personOne, "Update", "/api/person/v1", "PUT");
        assertLinks(personOne, "Delete", "/api/person/v1/1", "DELETE");
    }
}