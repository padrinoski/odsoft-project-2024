package pt.psoft.g1.psoftg1.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Librarian")
public class Librarian extends User {
    protected Librarian() {
        // for ORM only
    }
    public Librarian(String username, String password, String name) {
        super(username, password, name);
    }

    /**
     * factory method. since mapstruct does not handle protected/private setters
     * neither more than one public constructor, we use these factory methods for
     * helper creation scenarios
     *
     * @param username
     * @param password
     * @param name
     * @return
     */

    public static Librarian newLibrarian(final String username, final String password, final String name) {
        final var u = new Librarian(username, password, name);
        u.addAuthority(new Role(Role.LIBRARIAN));
        return u;
    }


}
