package pt.psoft.g1.psoftg1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private String authorNumber;
    private String name;
    private String bio;
    private String photoURI;
}