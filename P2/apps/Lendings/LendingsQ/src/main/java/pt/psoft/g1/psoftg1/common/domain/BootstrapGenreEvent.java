package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootstrapGenreEvent implements EventMsg {
    private String id;
    private String pk;
    private String genre;

}
