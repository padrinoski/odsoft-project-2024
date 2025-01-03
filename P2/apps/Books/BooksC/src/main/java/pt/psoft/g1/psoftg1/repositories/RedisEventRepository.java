package pt.psoft.g1.psoftg1.repositories;

import org.springframework.data.redis.core.RedisTemplate;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.EventType;
import pt.psoft.g1.psoftg1.common.domain.*;

import java.util.Objects;

public class RedisEventRepository extends RedisBaseRepository implements EventRepository{

    public RedisEventRepository(final RedisTemplate<String, Event> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public boolean bookExists(String isbn) {
        long creates = Objects.requireNonNull(listOperations.range(EventType.BOOK_CREATE.name(), 0, -1))
                .stream()
                .filter(event -> ((CreateBookEvent) event.getEventData()).getIsbn().equals(isbn))
                .count();

        long deletes = Objects.requireNonNull(listOperations.range(EventType.BOOK_DELETE.name(), 0, -1))
                .stream()
                .filter(event -> ((DeleteBookEvent) event.getEventData()).getIsbn().equals(isbn))
                .count();

        return creates > deletes;
    }

    @Override
    public boolean authorExists(String authorNumber) {
        long creates = Objects.requireNonNull(listOperations.range(EventType.AUTHOR_CREATE.name(), 0, -1))
                .stream()
                .filter(event -> ((CreateAuthorEvent) event.getEventData()).getAuthorNumber().equals(authorNumber))
                .count();

        long deletes = Objects.requireNonNull(listOperations.range(EventType.AUTHOR_DELETE.name(), 0, -1))
                .stream()
                .filter(event -> ((DeleteAuthorEvent) event.getEventData()).getAuthorNumber().equals(authorNumber))
                .count();

        return creates > deletes;
    }

    @Override
    public boolean genreExists(String pk) {
        long creates = Objects.requireNonNull(listOperations.range(EventType.GENRE_CREATE.name(), 0, -1))
                .stream()
                .filter(event -> ((CreateGenreEvent) event.getEventData()).getPk().equals(pk))
                .count();

        long deletes = Objects.requireNonNull(listOperations.range(EventType.GENRE_DELETE.name(), 0, -1))
                .stream()
                .filter(event -> ((DeleteGenreEvent) event.getEventData()).getPk().equals(pk))
                .count();

        return creates > deletes;
    }

    @Override
    public boolean isLibrarian(Long userId) {
        boolean isLibrarian = Objects.requireNonNull(listOperations.range(EventType.BOOT_USERS.name(), 0, -1))
                .stream()
                .filter(event -> ((BootstrapUserEvent) event.getEventData()).getUserId() == userId
                        && ((BootstrapUserEvent) event.getEventData()).getAuthorities().contains("LIBRARIAN"))
                .count() > 0;

        return isLibrarian;
    }

    @Override
    public boolean isReader(Long userId) {
        boolean isReader = Objects.requireNonNull(listOperations.range(EventType.BOOT_USERS.name(), 0, -1))
                .stream()
                .filter(event -> ((BootstrapUserEvent) event.getEventData()).getUserId() == userId
                        && ((BootstrapUserEvent) event.getEventData()).getAuthorities().contains("READER"))
                .count() > 0;

        return isReader;
    }

    @Override
    public Event getLastBookCreated(String isbn) {
        return Objects.requireNonNull(listOperations.range(EventType.BOOK_CREATE.name(), 0, -1))
                .stream()
                .filter(event -> ((CreateBookEvent) event.getEventData()).getIsbn().equals(isbn))
                .findFirst().get();
    }

    @Override
    public Event getLastAuthorCreated(String authorNumber) {
        return Objects.requireNonNull(listOperations.range(EventType.AUTHOR_CREATE.name(), 0, -1))
                .stream()
                .filter(event -> ((CreateAuthorEvent) event.getEventData()).getAuthorNumber().equals(authorNumber))
                .findFirst().get();
    }

    @Override
    public Event getLastGenreCreated(String pk) {
        return Objects.requireNonNull(listOperations.range(EventType.GENRE_CREATE.name(), 0, -1))
                .stream()
                .filter(event -> ((CreateGenreEvent) event.getEventData()).getPk().equals(pk))
                .findFirst().get();
    }
}
