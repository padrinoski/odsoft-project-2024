package pt.psoft.g1.psoftg1.repositories;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.common.Event;

import java.util.Optional;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import static pt.psoft.g1.psoftg1.common.EventType.*;



@Log4j2
@Repository
public class EventRepositoryImpl extends MongoBaseRepository<Event, String> implements EventRepository {

    public EventRepositoryImpl(final MongoTemplate mongoTemplate) {
        super(mongoTemplate, Event.class);
    }


    @Override
    public Event getLastEvent() {
        return null;
    }

    @Override
    public boolean userExists(String authorNumber) {
        Query queryCreates = new Query();
        queryCreates.addCriteria(Criteria.where("eventData.authorNumber").is(authorNumber).and("eventType").is(USER_CREATE.name()));

        Query queryDeletes = new Query();
        queryDeletes.addCriteria(Criteria.where("eventData.authorNumber").is(authorNumber).and("eventType").is(USER_DELETE.name()));

        long creates = mongoTemplate.find(queryCreates, Event.class).stream().count();
        long deletes = mongoTemplate.find(queryDeletes, Event.class).stream().count();

        return creates > deletes;
    }

    @Override
    public boolean readerExists(String authorNumber) {
        Query queryCreates = new Query();
        queryCreates.addCriteria(Criteria.where("eventData.authorNumber").is(authorNumber).and("eventType").is(READER_CREATE.name()));

        Query queryDeletes = new Query();
        queryDeletes.addCriteria(Criteria.where("eventData.authorNumber").is(authorNumber).and("eventType").is(READER_DELETE.name()));

        long creates = mongoTemplate.find(queryCreates, Event.class).stream().count();
        long deletes = mongoTemplate.find(queryDeletes, Event.class).stream().count();

        return creates > deletes;
    }
}
