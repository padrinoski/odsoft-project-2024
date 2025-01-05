package pt.psoft.g1.psoftg1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import pt.psoft.g1.psoftg1.interfaces.GetID;

@Node
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Fine implements GetID<String>{
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String pk;

    @PositiveOrZero
    private int fineValuePerDayInCents;

    /**Fine value in Euro cents*/
    @PositiveOrZero
    int centsValue;

    @Getter
    @Setter
    private String lendingNumber;

    public Fine(String lendingNumber) {
        this.lendingNumber = lendingNumber;
    }

    public Fine(String lendingNumber, int fineValuePerDayInCents, int centsValue) {
        this.lendingNumber = lendingNumber;
        this.fineValuePerDayInCents = fineValuePerDayInCents;
        this.centsValue = centsValue;
    }

    public Fine() {}

    @Override
    public String getID() {
        return this.pk;
    }

    public FineDTO toDTO() {
        return new FineDTO(this.pk, this.fineValuePerDayInCents, this.centsValue, this.lendingNumber);
    }
}