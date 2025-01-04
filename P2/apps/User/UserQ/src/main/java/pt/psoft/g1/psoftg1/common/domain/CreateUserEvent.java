package pt.psoft.g1.psoftg1.common.domain;

import pt.psoft.g1.psoftg1.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserEvent implements EventMsg{
    private String username;
    private String password;
    private String name;
    private Role authority;
}
