package pt.psoft.g1.psoftg1.dto;
import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderDTO {

    private String birthDate;

    private String phoneNumber;

    private List<String> interestList;
}
