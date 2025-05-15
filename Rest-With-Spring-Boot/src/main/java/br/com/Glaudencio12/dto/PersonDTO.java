package br.com.Glaudencio12.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PersonDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
}
