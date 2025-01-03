package pt.psoft.g1.psoftg1.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.model.Book;

import java.util.List;
import java.util.Optional;


/**
 *
 */
@NoRepositoryBean
public interface BookRepository extends CrudRepository<Book, String> {

    List<Book> findByGenre(@Param("genre") String genre);
    List<Book> findByTitle(@Param("title") String title);
    List<Book> findByAuthorName(@Param("authorName") String authorName);
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

}
