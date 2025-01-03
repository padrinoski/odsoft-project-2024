package pt.psoft.g1.psoftg1.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.common.Event;

public interface EventRepository extends CrudRepository<Event, String> {
    Event getLastEvent();
    boolean userExists(String authorNumber);
    boolean readerExists(String authorNumber);

}
