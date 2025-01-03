package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import pt.psoft.g1.psoftg1.interfaces.GetID;

@Entity
@NoArgsConstructor
public class ForbiddenName implements GetID<String> {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    private String pk;

    @Getter
    @Setter
    @Column(nullable = false)
    @Size(min = 1)
    private String forbiddenName;

    public ForbiddenName(String name) {
        this.forbiddenName = name;
    }

    @Override
    public String getID() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getID'");
    }
}
