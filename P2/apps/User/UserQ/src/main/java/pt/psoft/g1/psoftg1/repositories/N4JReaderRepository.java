package pt.psoft.g1.psoftg1.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.interfaces.ReaderRepository;
import pt.psoft.g1.psoftg1.model.Reader;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@EnableNeo4jRepositories
public class N4JReaderRepository extends N4JBaseRepository<Reader, String> implements ReaderRepository {
    public N4JReaderRepository(Neo4jTemplate neo4jTemplate) {
        super(neo4jTemplate, Reader.class);
    }

    @Override
    public List<Reader> findByPhoneNumber(String phoneNumber) {
        String cypherQuery = "MATCH (r:Reader) WHERE r.phoneNumber = $phoneNumber RETURN r";
        Map<String, Object> parameters = Map.of("phoneNumber", phoneNumber);
        return neo4jTemplate.findAll(cypherQuery, parameters, Reader.class);
    }

    @Override
    public Optional<Reader> findByUsername(String username) {
        String cypherQuery = "MATCH (r:Reader) WHERE r.username = $username RETURN r";
        Map<String, Object> parameters = Map.of("username", username);
        return neo4jTemplate.findOne(cypherQuery, parameters, Reader.class);
    }

}
