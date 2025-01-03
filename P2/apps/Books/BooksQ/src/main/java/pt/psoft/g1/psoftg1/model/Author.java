package pt.psoft.g1.psoftg1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import pt.psoft.g1.psoftg1.interfaces.GetID;

import java.io.Serializable;

@Node
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author implements GetID<String>, Serializable {

    @org.springframework.data.annotation.Id
    @Setter
    private String id;

    @Setter
    private String authorNumber;

    @org.springframework.data.annotation.Version
    public long version;

    @Setter
    private String name;

    @Setter
    private String bio;

    public String getID() {
        return authorNumber;
    }

    public Author(String authorNumber, String name, String bio, String photoURI) {
        setAuthorNumber(authorNumber);
        setName(name);
        setBio(bio);
    }

    public Author(String name, String bio, String photoURI) {
        setAuthorNumber(authorNumber);
        setName(name);
        setBio(bio);
    }

    public Author() {
        // got ORM only
    }
}

