package pt.psoft.g1.psoftg1.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.common.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//mongo db
@Repository
public interface EventRepository extends CrudRepository<Event,String> {
    boolean bookExists(String isbn);

    boolean authorExists(String authorNumber);

    boolean genreExists(String pk);

    boolean isLibrarian(Long userId);
    boolean isReader(Long userId);
    Event getLastBookCreated(String isbn);

    Event getLastAuthorCreated(String authorNumber);
    Event getLastGenreCreated(String pk);
}
