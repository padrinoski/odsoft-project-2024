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
    public Event getLastLendingCreated(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("eventType").is(LENDING_CREATE.name())
                .and("eventData.id").is(id));

        return mongoTemplate.find(query, Event.class).stream().findFirst().get();
    }

    @Override
    public Event getLastFineCreated(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("eventType").is(FINE_CREATE.name())
                .and("eventData.id").is(id));

        return mongoTemplate.find(query, Event.class).stream().findFirst().get();
    }

    @Override
    public boolean lendingExists(String id) {
        Query queryCreates = new Query();
        queryCreates.addCriteria(Criteria.where("eventData.id").is(id).and("eventType").is(LENDING_CREATE.name()));

        Query queryDeletes = new Query();
        queryDeletes.addCriteria(Criteria.where("eventData.id").is(id).and("eventType").is(LENDING_DELETE.name()));

        long creates = mongoTemplate.find(queryCreates, Event.class).stream().count();
        long deletes = mongoTemplate.find(queryDeletes, Event.class).stream().count();

        return creates > deletes;
    }

    @Override
    public boolean fineExists(String id) {
        Query queryCreates = new Query();
        queryCreates.addCriteria(Criteria.where("eventData.id").is(id).and("eventType").is(FINE_CREATE.name()));

        Query queryDeletes = new Query();
        queryDeletes.addCriteria(Criteria.where("eventData.id").is(id).and("eventType").is(FINE_DELETE.name()));

        long creates = mongoTemplate.find(queryCreates, Event.class).stream().count();
        long deletes = mongoTemplate.find(queryDeletes, Event.class).stream().count();

        return creates > deletes;
    }
}
