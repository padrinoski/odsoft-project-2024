package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.model.Role;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReaderEvent implements EventMsg{
    private String readerId;
    private String username;
    private String password;
    private String name;
    private String birthDate;
    private String phoneNumber;
    private List<String> interestList;
}
