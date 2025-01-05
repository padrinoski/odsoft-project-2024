package pt.psoft.g1.psoftg1.repositories;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.interfaces.FineRepository;
import pt.psoft.g1.psoftg1.model.Fine;

import java.util.Map;
import java.util.Optional;


@Component
@EnableNeo4jRepositories
public class N4JFineRepository extends N4JBaseRepository<Fine, String> implements FineRepository {

    public N4JFineRepository(Neo4jTemplate neo4jTemplate){
        super(neo4jTemplate, Fine.class);
    }

    @Override
    public Optional<Fine> getFineByLendingNumber(String lendingID) {
        String query = "MATCH (f:Fine {lendingId: $lendingID}) RETURN f";
        Fine fine = neo4jTemplate.findOne(query, Map.of("lendingID", lendingID), Fine.class).orElse(null);
        return Optional.ofNullable(fine);
    }

    @Override
    public Iterable<Fine> getAllFines() {
        String query = "MATCH (f:Fine) RETURN f";
        return neo4jTemplate.findAll(query, Fine.class);
    }
}
