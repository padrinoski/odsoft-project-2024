package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.model.Role;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReaderEvent implements EventMsg {
    private String username;
    private String password;
    private String name;
    private Set<Role> authorities;
    private String birthDate;
    private String phoneNumber;
    private List<String> interestList;
}
