package pt.psoft.g1.psoftg1.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import pt.psoft.g1.psoftg1.common.Event;

import static pt.psoft.g1.psoftg1.common.EventType.*;
import static pt.psoft.g1.psoftg1.common.EventType.AUTHOR_DELETE;

public class MongoEventRepository extends MongoBaseRepository<Event, String> implements EventRepository {
    public MongoEventRepository(MongoTemplate mongoTemplate) {
        super(mongoTemplate, Event.class);
    }

    @Override
    public boolean bookExists(String isbn) {
        Query queryCreates = new Query();
        queryCreates.addCriteria(Criteria.where("eventData.isbn").is(isbn).and("eventType").is(BOOK_CREATE.name()));

        Query queryDeletes = new Query();
        queryDeletes.addCriteria(Criteria.where("eventData.isbn").is(isbn).and("eventType").is(BOOK_DELETE.name()));

        long creates = mongoTemplate.find(queryCreates, Event.class).stream().count();
        long deletes = mongoTemplate.find(queryDeletes, Event.class).stream().count();


        return creates > deletes;
    }

    @Override
    public boolean authorExists(String authorNumber) {
        Query queryCreates = new Query();
        queryCreates.addCriteria(Criteria.where("eventData.authorNumber").is(authorNumber).and("eventType").is(AUTHOR_CREATE.name()));

        Query queryDeletes = new Query();
        queryDeletes.addCriteria(Criteria.where("eventData.authorNumber").is(authorNumber).and("eventType").is(AUTHOR_DELETE.name()));

        long creates = mongoTemplate.find(queryCreates, Event.class).stream().count();
        long deletes = mongoTemplate.find(queryDeletes, Event.class).stream().count();


        return creates > deletes;
    }

    @Override
    public boolean genreExists(String pk) {
        Query queryCreates = new Query();
        queryCreates.addCriteria(Criteria.where("eventData.pk").is(pk).and("eventType").is(GENRE_CREATE.name()));

        Query queryDeletes = new Query();
        queryDeletes.addCriteria(Criteria.where("eventData.pk").is(pk).and("eventType").is(GENRE_DELETE.name()));

        long creates = mongoTemplate.find(queryCreates, Event.class).stream().count();
        long deletes = mongoTemplate.find(queryDeletes, Event.class).stream().count();


        return creates > deletes;
    }

    @Override
    public boolean isLibrarian(Long userId) {
        Query queryManager = new Query();
        queryManager.addCriteria(Criteria.where("eventData.authorities").regex("LIBRARIAN")
                .and("eventData.userId").is(userId)
                .and("eventType").is(BOOT_USERS.name()));

        boolean isLibrarian = mongoTemplate.find(queryManager, Event.class).stream().count() > 0;
        return isLibrarian;
    }

    @Override
    public boolean isReader(Long userId) {
        Query queryManager = new Query();
        queryManager.addCriteria(Criteria.where("eventData.authorities").regex("READER")
                .and("eventData.userId").is(userId)
                .and("eventType").is(BOOT_USERS.name()));

        boolean isReader = mongoTemplate.find(queryManager, Event.class).stream().count() > 0;
        return isReader;
    }

    @Override
    public Event getLastBookCreated(String isbn) {
        Query query = new Query();
        query.addCriteria(Criteria.where("eventType").is(BOOK_CREATE.name())
                .and("eventData.isbn").is(isbn));

        return mongoTemplate.find(query, Event.class).stream().findFirst().get();
    }

    @Override
    public Event getLastAuthorCreated(String authorNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("eventType").is(AUTHOR_CREATE.name())
                .and("eventData.authorNumber").is(authorNumber));

        return mongoTemplate.find(query, Event.class).stream().findFirst().get();
    }

    @Override
    public Event getLastGenreCreated(String pk) {
        Query query = new Query();
        query.addCriteria(Criteria.where("eventType").is(GENRE_CREATE.name())
                .and("eventData.pk").is(pk));

        return mongoTemplate.find(query, Event.class).stream().findFirst().get();
    }
}
