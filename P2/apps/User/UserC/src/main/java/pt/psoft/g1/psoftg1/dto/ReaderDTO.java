package pt.psoft.g1.psoftg1.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Version;
import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO {

    private String birthDate;

    private String phoneNumber;

    private Long version;

    private List<String> interestList;
}
