package pt.psoft.g1.psoftg1.interfaces;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pt.psoft.g1.psoftg1.model.Genre;
import java.util.Optional;

@NoRepositoryBean
public interface GenreRepository extends CrudRepository<Genre,String> {

    Optional<Genre> findByGenre(String genre);

    Optional<Genre> findByPk(String pk);


}
