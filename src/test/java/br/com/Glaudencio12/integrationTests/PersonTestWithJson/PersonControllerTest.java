package br.com.Glaudencio12.integrationTests.PersonTestWithJson;

import br.com.Glaudencio12.config.TestConfigs;
import br.com.Glaudencio12.integrationTests.dto.PersonDTO;
import br.com.Glaudencio12.integrationTests.testContainer.AbstractIntegrationTest;
import br.com.Glaudencio12.unitTests.mocks.MockPerson;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {

    private static PersonDTO personDTO;
    private static ObjectMapper objectMapper;
    private static RequestSpecification specification;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    public PersonDTO mockPersonIntegration(Integer number) {
        PersonDTO person = new PersonDTO();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

    @Test
    @Order(1)
    void create() throws IOException {
        personDTO = mockPersonIntegration(1);

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(personDTO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        personDTO = createdPerson;

        assertNotNull(createdPerson.getId());
        assertNotNull(createdPerson.getFirstName());
        assertNotNull(createdPerson.getLastName());
        assertNotNull(createdPerson.getAddress());
        assertNotNull(createdPerson.getGender());

        assertTrue(createdPerson.getId() > 0);
    }

    @Test
    @Order(2)
    void findById() throws IOException {
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", personDTO.getId())
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO foundPerson = objectMapper.readValue(content, PersonDTO.class);

        assertNotNull(foundPerson);
        assertEquals(personDTO.getId(), foundPerson.getId());
        assertEquals(personDTO.getFirstName(), foundPerson.getFirstName());
        assertEquals(personDTO.getLastName(), foundPerson.getLastName());
        assertEquals(personDTO.getAddress(), foundPerson.getAddress());
        assertEquals(personDTO.getGender(), foundPerson.getGender());
    }

    @Test
    @Order(3)
    void findAll() throws IOException {
        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        List<PersonDTO> people = objectMapper.readValue(content, new TypeReference<List<PersonDTO>>() {});

        assertNotNull(people);
        assertFalse(people.isEmpty());
    }

    @Test
    @Order(4)
    void update() throws IOException {
        personDTO.setFirstName("Updated Name");
        personDTO.setLastName("Updated LastName");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .body(personDTO)
                .when()
                .put()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        PersonDTO updatedPerson = objectMapper.readValue(content, PersonDTO.class);

        assertNotNull(updatedPerson);
        assertEquals(personDTO.getId(), updatedPerson.getId());
        assertEquals("Updated Name", updatedPerson.getFirstName());
        assertEquals("Updated LastName", updatedPerson.getLastName());
    }

    @Test
    @Order(5)
    void delete() {
        given(specification)
                .pathParam("id", personDTO.getId())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

}
