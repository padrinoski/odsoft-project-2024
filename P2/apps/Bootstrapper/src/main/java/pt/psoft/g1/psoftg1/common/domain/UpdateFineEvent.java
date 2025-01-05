package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFineEvent implements EventMsg{
    private String pk;

    private int fineValuePerDayInCents;

    private int centsValue;

    private String lendingId;
}
