package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BootstrapUserEvent implements EventMsg {

    private Long userId;
    private String username;
    private String password;
    private String fullName;
    private Set authorities ;
    private String nif;
    private String morada;

    public BootstrapUserEvent(Long userId, String username, String password, String fullName, String nif, String morada) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.authorities = new HashSet();
        this.nif = nif;
        this.morada = morada;
    }
}
