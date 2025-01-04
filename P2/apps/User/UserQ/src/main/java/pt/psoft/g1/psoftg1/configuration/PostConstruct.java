package pt.psoft.g1.psoftg1.configuration;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
public class PostConstruct {
    @jakarta.annotation.PostConstruct
    public void createUniqueConstraints(final Driver driver) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("CREATE CONSTRAINT id_unique IF NOT EXISTS ON (u:User) ASSERT u.id IS UNIQUE");
                return null;
            });
        }
    }
}
