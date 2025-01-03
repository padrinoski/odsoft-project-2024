package pt.psoft.g1.psoftg1;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
public class PostConstruct {
    @jakarta.annotation.PostConstruct
    public void createUniqueConstraints(final Driver driver) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE CONSTRAINT isbn_unique IF NOT EXISTS ON (b:Book) ASSERT b.isbn IS UNIQUE");
                return null;
            });
        }
    }
}
