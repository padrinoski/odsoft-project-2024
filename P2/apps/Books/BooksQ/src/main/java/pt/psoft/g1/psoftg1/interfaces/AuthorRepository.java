package pt.psoft.g1.psoftg1.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pt.psoft.g1.psoftg1.model.Author;
import java.util.Optional;
@NoRepositoryBean
public interface AuthorRepository extends CrudRepository<Author, String> {

    Optional<Author> findByAuthorNumber(String authorNumber);

}
