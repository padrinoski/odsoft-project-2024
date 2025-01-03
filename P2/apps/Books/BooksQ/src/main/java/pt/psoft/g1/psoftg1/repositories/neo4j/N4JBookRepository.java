package pt.psoft.g1.psoftg1.repositories.neo4j;

import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.interfaces.BookRepository;
import pt.psoft.g1.psoftg1.model.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@EnableNeo4jRepositories
public class N4JBookRepository extends N4JBaseRepository<Book, String> implements BookRepository {

    public N4JBookRepository(final Neo4jTemplate neo4jTemplate){
        super(neo4jTemplate, Book.class);
    }
    @Override
    public List<Book> findByGenre(String genre) {
        String cypherQuery = "MATCH (b:Book) WHERE b.genre = $genre RETURN b";
        Map<String, Object> parameters = Map.of("genre", genre);
        return neo4jTemplate.findAll(cypherQuery, parameters, Book.class);
    }

    @Override
    public List<Book> findByTitle(String title) {
        String cypherQuery = "MATCH (b:Book) WHERE b.title = $title RETURN b";
        Map<String, Object> parameters = Map.of("genre", title);
        return neo4jTemplate.findAll(cypherQuery, parameters, Book.class);
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        return null;
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        String cypherQuery = "MATCH (b:Book) WHERE b.isbn = $isbn RETURN b";
        Map<String, Object> parameters = Map.of("isbn", isbn);
        return neo4jTemplate.findOne(cypherQuery, parameters, Book.class);
    }
}
