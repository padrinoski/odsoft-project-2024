package pt.psoft.g1.psoftg1.repositories;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.interfaces.ReaderRepository;
import pt.psoft.g1.psoftg1.interfaces.UserRepository;
import pt.psoft.g1.psoftg1.model.Reader;
import pt.psoft.g1.psoftg1.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@EnableNeo4jRepositories
public class N4JUserRepository extends N4JBaseRepository<User, String> implements UserRepository {
    public N4JUserRepository(Neo4jTemplate neo4jTemplate) {
        super(neo4jTemplate, User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String cypherQuery = "MATCH (u:User) WHERE u.username = $username RETURN u";
        Map<String, Object> parameters = Map.of("username", username);
        return neo4jTemplate.findOne(cypherQuery, parameters, User.class);
    }

    @Override
    public List<User> findByName(String name) {
        String cypherQuery = "MATCH (u:User) WHERE u.name = name RETURN u";
        Map<String, Object> parameters = Map.of("name", name);
        return neo4jTemplate.findAll(cypherQuery, parameters, User.class);
    }

}
