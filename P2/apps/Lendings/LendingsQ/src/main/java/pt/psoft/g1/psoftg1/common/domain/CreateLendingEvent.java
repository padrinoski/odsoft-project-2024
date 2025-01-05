package pt.psoft.g1.psoftg1.common.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLendingEvent implements EventMsg{

    public CreateLendingEvent(String bookId, String userId, String userEmail, String lendingNumber, LocalDate startDate, LocalDate limitDate, LocalDate returnedDate, Integer fineValuePerDayInCents) {
        this.bookId = bookId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.lendingNumber = lendingNumber;
        this.startDate = startDate;
        this.limitDate = limitDate;
        this.returnedDate = returnedDate;
        this.fineValuePerDayInCents = fineValuePerDayInCents;
    }

    private String bookId;

    private String userId;

    private String userEmail;

    private String lendingNumber;

    private LocalDate startDate;

    private LocalDate limitDate;

    private LocalDate returnedDate;

    private Integer fineValuePerDayInCents;

    private Integer seq;

    private Integer lendingDuration;
}
