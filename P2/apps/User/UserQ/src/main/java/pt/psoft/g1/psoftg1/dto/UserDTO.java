package pt.psoft.g1.psoftg1.dto;

import lombok.*;
import pt.psoft.g1.psoftg1.model.Role;


import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private String name;
    private Role authority;
}
