package pt.psoft.g1.psoftg1.repository;

import pt.psoft.g1.psoftg1.common.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//mongo db
@Repository
public interface EventRepository extends MongoRepository<Event,String> {

}
