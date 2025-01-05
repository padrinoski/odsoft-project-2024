package pt.psoft.g1.psoftg1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.neo4j.ogm.annotation.Transient;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import pt.psoft.g1.psoftg1.interfaces.GetID;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Node
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Lending implements GetID<String> {

    @Id
    public String pk;

    @Getter
    @NotNull
    @Size(min = 6, max = 32)
    public String lendingNumber;

    @NotNull
    @Getter
    public String bookId;


    @Getter
    public String userId;

    @Getter
    private String userEmail;

    @NotNull
    @Getter
    public LocalDate startDate;

    @NotNull
    @Getter
    public LocalDate limitDate;

    @Getter
    public LocalDate returnedDate;

    @org.springframework.data.annotation.Version
    @Getter
    private long version;

    @Size(min = 0, max = 1024)
    private String commentary = null;

    @Transient
    private Integer daysUntilReturn;

    @Transient
    private Integer daysOverdue;

    @Getter
    private int fineValuePerDayInCents;

    public Lending(String bookId, String userId,String userEmail, String lendingNumber, int lendingDuration, int fineValuePerDayInCents) {
        this.bookId = bookId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.lendingNumber = lendingNumber;
        this.startDate = LocalDate.now();
        this.limitDate = LocalDate.now().plusDays(lendingDuration);
        this.returnedDate = null;
        this.fineValuePerDayInCents = fineValuePerDayInCents;
        setDaysUntilReturn();
        setDaysOverdue();
    }

    public String generateLendingNumber(int seq) {
        int year = LocalDate.now().getYear();
        if (seq < 0) {
            throw new IllegalArgumentException("Sequential component cannot be negative");
        }
        return year + "/" + seq;
    }

    public void validateAndSetLendingNumber(String lendingNumber) {
        if (lendingNumber == null || lendingNumber.length() < 6 || lendingNumber.length() > 32) {
            throw new IllegalArgumentException("Lending number must be between 6 and 32 characters");
        }
        try {
            int year = Integer.parseInt(lendingNumber.substring(0, 4));
            if (year < 1970 || year > LocalDate.now().getYear()) {
                throw new IllegalArgumentException("Invalid year component in lending number");
            }
            if (lendingNumber.charAt(4) != '/') {
                throw new IllegalArgumentException("Lending number must be in the format {year}/{sequential}");
            }
            Integer.parseInt(lendingNumber.substring(5));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid format for lending number");
        }
        this.lendingNumber = lendingNumber;
    }

    public void setReturned(final long desiredVersion, final String commentary) {
        if (this.returnedDate != null)
            throw new IllegalArgumentException("Book has already been returned!");

        if (commentary != null)
            this.commentary = commentary;

        this.returnedDate = LocalDate.now();
    }

    public int getDaysDelayed() {
        if (this.returnedDate != null) {
            return Math.max((int) ChronoUnit.DAYS.between(this.limitDate, this.returnedDate), 0);
        } else {
            return Math.max((int) ChronoUnit.DAYS.between(this.limitDate, LocalDate.now()), 0);
        }
    }

    public void setDaysUntilReturn() {
        int daysUntilReturn = (int) ChronoUnit.DAYS.between(LocalDate.now(), this.limitDate);
        if (this.returnedDate != null || daysUntilReturn < 0) {
            this.daysUntilReturn = null;
        } else {
            this.daysUntilReturn = daysUntilReturn;
        }
    }

    public void setDaysOverdue() {
        int days = getDaysDelayed();
        if (days > 0) {
            this.daysOverdue = days;
        } else {
            this.daysOverdue = null;
        }
    }

    public Optional<Integer> getDaysUntilReturn() {
        setDaysUntilReturn();
        return Optional.ofNullable(daysUntilReturn);
    }

    public Optional<Integer> getDaysOverdue() {
        setDaysOverdue();
        return Optional.ofNullable(daysOverdue);
    }

    public Optional<Integer> getFineValueInCents() {
        Optional<Integer> fineValueInCents = Optional.empty();
        int days = getDaysDelayed();
        if (days > 0) {
            fineValueInCents = Optional.of(fineValuePerDayInCents * days);
        }
        return fineValueInCents;
    }

    public Lending() {}

    public static Lending newBootstrappingLending(String bookId, String userId, String lendingNumber, LocalDate startDate,
                                                  LocalDate returnedDate, int lendingDuration, int fineValuePerDayInCents) {
        Lending lending = new Lending();
        lending.bookId = bookId;
        lending.userId = userId;
        lending.validateAndSetLendingNumber(lendingNumber);
        lending.startDate = startDate;
        lending.limitDate = startDate.plusDays(lendingDuration);
        lending.fineValuePerDayInCents = fineValuePerDayInCents;
        lending.returnedDate = returnedDate;
        return lending;
    }

    @Override
    public String getID() {
        return this.pk;
    }

    public LendingDTO toDTO() {
        return new LendingDTO(this.getBookId(), this.getUserId(),this.getUserEmail(), this.getLendingNumber(), this.getStartDate(), this.getLimitDate(), this.getReturnedDate(), this.getFineValuePerDayInCents());
    }
}