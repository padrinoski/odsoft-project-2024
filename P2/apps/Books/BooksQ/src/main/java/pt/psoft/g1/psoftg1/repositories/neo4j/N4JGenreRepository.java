package pt.psoft.g1.psoftg1.repositories.neo4j;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.interfaces.BookRepository;
import pt.psoft.g1.psoftg1.interfaces.GenreRepository;
import pt.psoft.g1.psoftg1.model.Book;
import pt.psoft.g1.psoftg1.model.Genre;

import java.util.Map;
import java.util.Optional;

@Component
@EnableNeo4jRepositories
public class N4JGenreRepository extends N4JBaseRepository<Genre, String> implements GenreRepository {
    public N4JGenreRepository(Neo4jTemplate neo4jTemplate) {
        super(neo4jTemplate, Genre.class);
    }

    @Override
    public Optional<Genre> findByGenre(String genre) {
        String cypherQuery = "MATCH (g:Genre) WHERE g.genre = $genre RETURN g";
        Map<String, Object> parameters = Map.of("genre", genre);
        return neo4jTemplate.findOne(cypherQuery, parameters, Genre.class);
    }

    @Override
    public Optional<Genre> findByPk(String pk) {
        String cypherQuery = "MATCH (g:Genre) WHERE g.pk = $pk RETURN g";
        Map<String, Object> parameters = Map.of("pk", pk);
        return neo4jTemplate.findOne(cypherQuery, parameters, Genre.class);
    }
}
