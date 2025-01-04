package pt.psoft.g1.psoftg1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.dto.ReaderDTO;

import java.util.List;

@Entity
@DiscriminatorValue("Reader")
public class Reader extends User {

    @Getter
    private String birthDate;

    @Getter
    private String phoneNumber;

/*    @Setter
    @Getter
    @Basic
    private boolean gdprConsent;

    @Setter
    @Basic
    @Getter
    private boolean marketingConsent;

    @Setter
    @Basic
    @Getter
    private boolean thirdPartySharingConsent;*/

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

    public Reader(String username, String password, String name, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty, List<String> interestList) {
        super(username, password, name);
        this.addAuthority(new Role(Role.READER));
/*
        this.gdprConsent = gdpr;
        this.marketingConsent = marketing;
        this.thirdPartySharingConsent = thirdParty;*/
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

    public static Reader newReader(final String username, final String password, final String name, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty, List<String> interestList) {
        final var u = new Reader(username, password, name, birthDate, phoneNumber, gdpr, marketing, thirdParty, interestList);
        return u;
    }

    public static Reader newReader(final String username, final String password, final String name) {
        final var u = new Reader(username, password, name);
        return u;
    }


    public ReaderDTO toReaderDTO() {
        return new ReaderDTO( this.birthDate, this.phoneNumber, this.interestList);
    }
}
