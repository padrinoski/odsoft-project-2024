package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReaderEvent implements EventMsg {
    private String birthDate;

    private String phoneNumber;

    private Long version;

    private List<String> interestList;
}
