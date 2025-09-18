package br.com.Glaudencio12.dto;

import br.com.Glaudencio12.serializer.GenderSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@NoArgsConstructor
@Data
@Relation(collectionRelation = "Person") // ALTERA O NOME DO OBJETO PERSON QUE O HAL RETORNA
public class PersonDTO extends RepresentationModel<PersonDTO> {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;
    private boolean enabled;
}