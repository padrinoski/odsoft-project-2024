package pt.psoft.g1.psoftg1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import pt.psoft.g1.psoftg1.interfaces.GetID;

import java.util.Objects;

/**
 * The {@code Fine} class models a fine applied when a lending is past its due date.
 * <p>It stores its current value, and the associated {@code Lending}.
 * @author  rmfranca*/
@Getter
@Entity
@Document(collection = "fine")
public class Fine implements GetID<String>{
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    private String pk;

    @PositiveOrZero
    @Column(updatable = false)
    @Field(name = "fineValuePerDayInCents")
    private int fineValuePerDayInCents;

    /**Fine value in Euro cents*/
    @PositiveOrZero
    @Field(name = "centsValue")
    int centsValue;

    @Setter
    @JoinColumn(name = "lending_pk", nullable = false, unique = true)
    @Field(name = "lending")
    private String lendingId;

    public Fine(String lendingId) {
        this.lendingId = lendingId;
    }

    /**Protected empty constructor for ORM only.*/
    protected Fine() {}

    @Override
    public String getID() {
        return this.pk;
    }
}
