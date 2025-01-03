package pt.psoft.g1.psoftg1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.neo4j.core.schema.Node;
import pt.psoft.g1.psoftg1.interfaces.GetID;

import java.io.Serializable;

@Node
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre implements GetID<String>, Serializable {
    @Transient
    private static final int GENRE_MAX_LENGTH = 100;
    @org.springframework.data.annotation.Id
    String id;
    //@GeneratedValue(strategy= GenerationType.AUTO)
    @Setter
    @Getter
    String pk;

    @Size(min = 1, max = GENRE_MAX_LENGTH, message = "Genre name must be between 1 and 100 characters")
    @Getter
    String genre;

    protected Genre(){}

    public Genre(String genre) {
        setGenre(genre);
    }

    public void setGenre(String genre) {
        if(genre == null)
            throw new IllegalArgumentException("Genre cannot be null");
        if(genre.isBlank())
            throw new IllegalArgumentException("Genre cannot be blank");
        if(genre.length() > GENRE_MAX_LENGTH)
            throw new IllegalArgumentException("Genre has a maximum of 4096 characters");
        this.genre = genre;
    }

    public String toString() {
        return genre;
    }

    @Override
    public String getID() {
        return this.pk;
    }

    public GenreDTO toDTO(){
        return new GenreDTO(this.pk,this.genre);
    }
}
