package pt.psoft.g1.psoftg1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String isbn;
    private String title;
    private String description;
    private String genre;
    private List<String> authors;
    private String photoURI;
}