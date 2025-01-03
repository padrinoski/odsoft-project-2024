package pt.psoft.g1.psoftg1.views;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class ReaderView {
    private String readerNumber;
    private String email;
    private String fullName;
    private String birthDate;
    private String phoneNumber;
    private String photo;
    private boolean gdprConsent;
    private boolean marketingConsent;
    private boolean thirdPartySharingConsent;
    private List<String> interestList;
}
