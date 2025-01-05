package pt.psoft.g1.psoftg1.repositories;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.interfaces.LendingRepository;
import pt.psoft.g1.psoftg1.model.Lending;

import java.util.Map;
import java.util.Optional;

@Component
@EnableNeo4jRepositories
public class N4JLendingRepository extends N4JBaseRepository<Lending, String> implements LendingRepository {

    public N4JLendingRepository(Neo4jTemplate neo4jTemplate) {
        super(neo4jTemplate, Lending.class);
    }

    @Override
    public Optional<Lending> getLendingByLendingNumber(String lendingNumber) {
        String query = "MATCH (l:Lending {lendingNumber: $lendingNumber}) RETURN l";
        Lending lending = neo4jTemplate.findOne(query, Map.of("lendingNumber", lendingNumber), Lending.class).orElse(null);
        return Optional.ofNullable(lending);
    }

    @Override
    public Iterable<Lending> getAllLendings() {
        String query = "MATCH (l:Lending) RETURN l";
        return neo4jTemplate.findAll(query, Lending.class);
    }

    @Override
    public Iterable<Lending> getAllLendingsForABook(String bookId) {
        String query = "MATCH (l:Lending {bookId: $bookId}) RETURN l";
        return neo4jTemplate.findAll(query, Map.of("bookId", bookId), Lending.class);
    }
}
