package pt.psoft.g1.psoftg1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.StaleObjectStateException;
import org.hibernate.annotations.UuidGenerator;
import pt.psoft.g1.psoftg1.interfaces.GetID;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LENDING_NUMBER"})})
public class Lending implements GetID<String> {

//    @Id
//    @UuidGenerator
//    private String id;

    @Id
    @UuidGenerator
    private String pk;

    @Getter
    @NotNull
    @Size(min = 6, max = 32)
    @Column(name = "LENDING_NUMBER", unique = true, nullable = false, length = 32)
    private String lendingNumber;

    @NotNull
    @Getter
    private String bookId;

    @Getter
    private String userId;

    @Getter
    private String userEmail;

    @NotNull
    @Column(nullable = false, updatable = false)
    @Getter
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    @Getter
    private LocalDate limitDate;

    @Temporal(TemporalType.DATE)
    @Getter
    private LocalDate returnedDate;

    @Version
    @org.springframework.data.annotation.Version
    @Getter
    private long version;

    @Size(min = 0, max = 1024)
    @Column(length = 1024)
    private String commentary = null;

    @Transient
    private Integer daysUntilReturn;

    @Transient
    private Integer daysOverdue;

    @Getter
    private int fineValuePerDayInCents;

    public Lending(String bookId, String userId, int seq, int lendingDuration, int fineValuePerDayInCents) {
        this.bookId = bookId;
        this.userId = userId;
        this.lendingNumber = generateLendingNumber(seq);
        this.startDate = LocalDate.now();
        this.limitDate = LocalDate.now().plusDays(lendingDuration);
        this.returnedDate = null;
        this.fineValuePerDayInCents = fineValuePerDayInCents;
        setDaysUntilReturn();
        setDaysOverdue();
    }

    private String generateLendingNumber(int seq) {
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

    public void setReturned(final long desiredVersion, final String commentary){

        if (this.returnedDate != null)
            throw new IllegalArgumentException("book has already been returned!");

        // check current version
        if (this.version != desiredVersion)
            throw new StaleObjectStateException("Object was already modified by another user", this.pk);

        if(commentary != null)
            this.commentary = commentary;

        this.returnedDate = LocalDate.now();
    }

    public int getDaysDelayed(){
        if(this.returnedDate != null) {
            return Math.max((int) ChronoUnit.DAYS.between(this.limitDate, this.returnedDate), 0);
        }else{
            return Math.max((int) ChronoUnit.DAYS.between(this.limitDate, LocalDate.now()), 0);
        }
    }

    private void setDaysUntilReturn(){
        int daysUntilReturn = (int) ChronoUnit.DAYS.between(LocalDate.now(), this.limitDate);
        if(this.returnedDate != null || daysUntilReturn < 0){
            this.daysUntilReturn = null;
        }else{
            this.daysUntilReturn = daysUntilReturn;
        }
    }

    private void setDaysOverdue(){
        int days = getDaysDelayed();
        if(days > 0){
            this.daysOverdue = days;
        }else{
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
        if(days > 0){
            fineValueInCents = Optional.of(fineValuePerDayInCents * days);
        }
        return fineValueInCents;
    }


    protected Lending() {}

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
        return new LendingDTO(this.getBookId(), this.getUserId(), this.getUserEmail(), this.getLendingNumber(), this.getStartDate(), this.getLimitDate(), this.getReturnedDate(), this.getFineValuePerDayInCents());
    }
}
