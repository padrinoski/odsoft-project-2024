package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookEvent implements EventMsg{
    private String isbn;
    private String title;
    private String description;
    private String genreId;
    private List<String> authorIds;
    private String photoURI;
}
