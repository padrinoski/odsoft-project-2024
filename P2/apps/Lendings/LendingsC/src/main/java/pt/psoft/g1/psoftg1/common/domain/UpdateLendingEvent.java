package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateLendingEvent implements EventMsg{

    private String lendingId;

    private String bookId;

    private String userId;

    private String lendingNumber;

    private LocalDate startDate;

    private LocalDate limitDate;

    private LocalDate returnedDate;

    private Integer fineValuePerDayInCents;
}
