package pt.psoft.g1.psoftg1.repositories.neo4j;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.interfaces.AuthorRepository;
import pt.psoft.g1.psoftg1.interfaces.BookRepository;
import pt.psoft.g1.psoftg1.model.Author;
import pt.psoft.g1.psoftg1.model.Book;

import java.util.Map;
import java.util.Optional;

@Component
@EnableNeo4jRepositories
public class N4JAuthorRepository extends N4JBaseRepository<Author, String> implements AuthorRepository {

    public N4JAuthorRepository(Neo4jTemplate neo4jTemplate) {
        super(neo4jTemplate, Author.class);
    }

    @Override
    public Optional<Author> findByAuthorNumber(String authorNumber) {
        String cypherQuery = "MATCH (a:Author) WHERE a.authorNumber = $authorNumber RETURN a";
        Map<String, Object> parameters = Map.of("authorNumber", authorNumber);
        return neo4jTemplate.findOne(cypherQuery, parameters, Author.class);
    }
}
