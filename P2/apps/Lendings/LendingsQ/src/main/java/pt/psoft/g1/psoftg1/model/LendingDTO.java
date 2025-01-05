package pt.psoft.g1.psoftg1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class LendingDTO {

    private String bookId;

    private String userId;

    private String userEmail;

    private String lendingNumber;

    private LocalDate startDate;

    private LocalDate limitDate;

    private LocalDate returnedDate;

    private Integer fineValueInCents;
}
