package br.com.Glaudencio12.dto;

import br.com.Glaudencio12.serializer.GenderSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Data
public class PersonDTO extends RepresentationModel<PersonDTO> {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;
}