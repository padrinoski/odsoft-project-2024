package pt.psoft.g1.psoftg1.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.interfaces.GetID;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book  implements GetID<String>, Serializable {
    @Id
    @UuidGenerator
    @org.springframework.data.annotation.Id
    String pk;

    @Version
    @org.springframework.data.annotation.Version
    private Long version;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    @Indexed(unique = true)
    String isbn;

    @Setter
    @Column(nullable = false)
    String title;

    @Setter
    @ManyToOne
    @NotNull
    Genre genre;

    @Setter
    @Getter
    @ManyToMany
    private List<Author> authors = new ArrayList<>();

    @Setter
    @Getter
    @Column(nullable = false)
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
