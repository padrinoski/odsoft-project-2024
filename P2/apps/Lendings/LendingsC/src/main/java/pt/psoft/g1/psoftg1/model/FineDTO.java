package pt.psoft.g1.psoftg1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FineDTO {
    private String pk;

    private int fineValuePerDayInCents;

    private int centsValue;

    private String lendingId;


}
