package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootstrapAuthorEvent implements EventMsg{
    private String id;
    private String authorNumber;
    private String name;
    private String bio;
    private String photoURI;

}
