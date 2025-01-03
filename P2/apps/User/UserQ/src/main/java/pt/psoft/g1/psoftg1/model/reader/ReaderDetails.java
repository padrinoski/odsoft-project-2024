package pt.psoft.g1.psoftg1.model.reader;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pt.psoft.g1.psoftg1.dto.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.interfaces.GetID;
import pt.psoft.g1.psoftg1.model.user.Reader;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;

import java.nio.file.InvalidPathException;
import java.util.List;

@Entity
@Table(name = "READER_DETAILS")
public class ReaderDetails extends EntityWithPhoto implements GetID<String> {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    private String pk;

    @Getter
    @Setter
    @OneToOne
    private Reader reader;

    private ReaderNumber readerNumber;

    @Embedded
    @Getter
    private BirthDate birthDate;

    @Embedded
    private PhoneNumber phoneNumber;

    @Setter
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
    private boolean thirdPartySharingConsent;

    @Version
    @org.springframework.data.annotation.Version
    @Getter
    private Long version;

    @Getter
    @Setter
    @ElementCollection
    private List<String> interestList;

    public ReaderDetails(int readerNumber, Reader reader, String birthDate, String phoneNumber, boolean gdpr, boolean marketing, boolean thirdParty, String photoURI, List<String> interestList) {
        if(reader == null || phoneNumber == null) {
            throw new IllegalArgumentException("Provided argument resolves to null object");
        }

        if(!gdpr) {
            throw new IllegalArgumentException("Readers must agree with the GDPR rules");
        }

        setReader(reader);
        setReaderNumber(new ReaderNumber(readerNumber));
        setPhoneNumber(new PhoneNumber(phoneNumber));
        setBirthDate(new BirthDate(birthDate));
        //By the client specifications, gdpr can only have the value of true. A setter will be created anyways in case we have accept no gdpr consent later on the project
        setGdprConsent(true);

        setPhotoInternal(photoURI);
        setMarketingConsent(marketing);
        setThirdPartySharingConsent(thirdParty);
        setInterestList(interestList);
    }

    private void setPhoneNumber(PhoneNumber number) {
        if(number != null) {
            this.phoneNumber = number;
        }
    }

    private void setReaderNumber(ReaderNumber readerNumber) {
        if(readerNumber != null) {
            this.readerNumber = readerNumber;
        }
    }

    private void setBirthDate(BirthDate date) {
        if(date != null) {
            this.birthDate = date;
        }
    }

    public void applyPatch(final long currentVersion, final UpdateReaderRequest request, String photoURI, List<String> interestList) {
        if(currentVersion != this.version) {
            throw new ConflictException("Provided version does not match latest version of this object");
        }

        String birthDate = request.getBirthDate();
        String phoneNumber = request.getPhoneNumber();
        boolean marketing = request.getMarketing();
        boolean thirdParty = request.getThirdParty();
        String fullName = request.getFullName();
        String username = request.getUsername();
        String password = request.getPassword();

        if(username != null) {
            this.reader.setUsername(username);
        }

        if(password != null) {
            this.reader.setPassword(password);
        }

        if(fullName != null) {
            this.reader.setName(fullName);
        }

        if(birthDate != null) {
            setBirthDate(new BirthDate(birthDate));
        }

        if(phoneNumber != null) {
            setPhoneNumber(new PhoneNumber(phoneNumber));
        }

        if(marketing != this.marketingConsent) {
            setMarketingConsent(marketing);
        }

        if(thirdParty != this.thirdPartySharingConsent) {
            setThirdPartySharingConsent(thirdParty);
        }

        if(photoURI != null) {
            try {
                setPhotoInternal(photoURI);
            } catch(InvalidPathException ignored) {}
        }

        if(interestList != null) {
            this.interestList = interestList;
        }
    }

    public void removePhoto(long desiredVersion) {
        if(desiredVersion != this.version) {
            throw new ConflictException("Provided version does not match latest version of this object");
        }

        setPhotoInternal(null);
    }

    public String getReaderNumber(){
        return this.readerNumber.toString();
    }

    public String getPhoneNumber() { return this.phoneNumber.toString();}

    protected ReaderDetails() {
        // for ORM only
    }

    @Override
    public String getID() {
        return this.pk;
    }
}
