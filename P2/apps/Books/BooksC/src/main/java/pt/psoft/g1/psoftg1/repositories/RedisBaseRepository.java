package pt.psoft.g1.psoftg1.repositories;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.common.Event;

import java.util.ArrayList;
import java.util.Optional;

public class RedisBaseRepository implements CrudRepository<Event, String> {

    protected final RedisTemplate<String, Event> redisTemplate;

    protected ListOperations<String, Event> listOperations;


    public RedisBaseRepository(final RedisTemplate<String, Event> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void setHashOperations() {
        listOperations = redisTemplate.opsForList();
    }

    @Override
    public Event save(final Event event) {
        listOperations.rightPush(event.getEventType().name(), event);
        return event;
    }

    @Override
    public <S extends Event> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(s -> listOperations.rightPush(s.getEventType().name(), s));
        return entities;
    }

    @Override
    public Optional<Event> findById(String id) {
        return Optional.empty();
    }


    @Override
    public boolean existsById(String aLong) {
        return false;
    }

    @Override
    public Iterable<Event> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Iterable<Event> findAllById(Iterable<String> ids) {
        return new ArrayList<>();
    }


    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public void delete(Event entity) {
    }


    @Override
    public void deleteAllById(Iterable<? extends String> ids) {
    }

    @Override
    public void deleteAll(final Iterable<? extends Event> entities) {
    }

    @Override
    public void deleteAll() {
    }
}
