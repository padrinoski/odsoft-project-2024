package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAuthorEvent implements EventMsg {
    private String authorNumber;
    private String name;
    private String bio;
    private String photoURI;
}
