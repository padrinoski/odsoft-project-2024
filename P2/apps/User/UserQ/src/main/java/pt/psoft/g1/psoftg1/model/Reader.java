package pt.psoft.g1.psoftg1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Node;
import pt.psoft.g1.psoftg1.dto.ReaderDTO;

import java.util.List;

@Node
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reader extends User {

    @Setter
    @Getter
    private String birthDate;

    @Setter
    @Getter
    private String phoneNumber;

    @Getter
    @Setter
    @ElementCollection
    private List<String> interestList;

    protected Reader() {
        // for ORM only
    }

    public Reader(String username, String password, String name){
        super(username, password, name);
        this.addAuthority(new Role(Role.READER));
    }

    public Reader(String username, String password, String name, String birthDate, String phoneNumber, List<String> interestList) {
        super(username, password, name);
        this.addAuthority(new Role(Role.READER));
        this.interestList = interestList;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
    }

    /**
     * factory method. since mapstruct does not handle protected/private setters
     * neither more than one public constructor, we use these factory methods for
     * helper creation scenarios
     *
     * @param username
     * @param password
     * @param name
     * @return
     */

    public static Reader newReader(final String username, final String password, final String name, String birthDate, String phoneNumber,List<String> interestList) {
        final var u = new Reader(username, password, name, birthDate, phoneNumber, interestList);
        return u;
    }

    public static Reader newReader(final String username, final String password, final String name) {
        final var u = new Reader(username, password, name);
        return u;
    }


    public ReaderDTO toReaderDTO() {
        return new ReaderDTO(this.getUsername(), this.getPassword(), this.getName(), this.birthDate, this.phoneNumber, this.interestList);
    }
}
