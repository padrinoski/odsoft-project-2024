package pt.psoft.g1.psoftg1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pt.psoft.g1.psoftg1.interfaces.GetID;

import java.io.Serializable;

@Entity
@Data
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author  implements GetID<String>, Serializable {

    // primary key for MongoDB (_id) not used by JPA
    @Id
    @UuidGenerator
    @org.springframework.data.annotation.Id
    @Setter
    private String id;

    @Column(unique = true, nullable = false)
    @Indexed(unique = true)
    @Setter
    @Field("authorNumber")
    private String authorNumber;

    @Version
    @org.springframework.data.annotation.Version
    public long version;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
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

