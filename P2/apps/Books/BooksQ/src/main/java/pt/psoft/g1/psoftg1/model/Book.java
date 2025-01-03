package pt.psoft.g1.psoftg1.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import pt.psoft.g1.psoftg1.interfaces.GetID;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Node
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book implements GetID<String>, Serializable {
    @org.springframework.data.annotation.Id
    String pk;

    @org.springframework.data.annotation.Version
    private Long version;

    @Getter
    @Setter
    String isbn;

    @Setter
    String title;

    @Setter
    @NotNull
    @Relationship(type = "HAS_GENRE", direction = Relationship.Direction.OUTGOING) // Define relationship
    Genre genre;

    @Setter
    @Getter
    @Relationship(type = "HAS_AUTHORS", direction = Relationship.Direction.OUTGOING)
    private List<Author> authors = new ArrayList<>();

    @Setter
    @Getter
    String description;

    public Book(String isbn, String title, String description, Genre genre, List<Author> authors, String photoURI) {
        setTitle(title);
        setIsbn(isbn);
        if (description != null)
            setDescription(description);
        if (genre == null)
            throw new IllegalArgumentException("Genre cannot be null");
        setGenre(genre);
        if (authors == null)
            throw new IllegalArgumentException("Author list is null");
        if (authors.isEmpty())
            throw new IllegalArgumentException("Author list is empty");

        setAuthors(authors);
    }

    protected Book() {
        // got ORM only
    }

     public BookDTO toDTO(){
        List<String> authorIds = this.authors.stream()
                .map(Author::getAuthorNumber)
                .toList();

        return new BookDTO(this.isbn, this.title, this.description, this.genre.genre, authorIds, null );
    }
    @Override
    public String getID() {
        return this.pk;
    }
}
