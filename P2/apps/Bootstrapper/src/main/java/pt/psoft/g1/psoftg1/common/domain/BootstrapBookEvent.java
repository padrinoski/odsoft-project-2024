package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootstrapBookEvent  implements EventMsg{
    private String id;
    private String isbn;
    private String title;
    private String description;
    private String genre;
    private List<String> authors;
    private String photoURI;

}