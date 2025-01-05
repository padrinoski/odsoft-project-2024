package pt.psoft.g1.psoftg1.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.common.Event;

public interface EventRepository extends CrudRepository<Event, String> {
    Event getLastLendingCreated(String id);
    Event getLastFineCreated(String id);
    boolean lendingExists(String id);
    boolean fineExists(String id);

}
