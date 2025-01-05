package pt.psoft.g1.psoftg1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.common.ApplicationType;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.EventType;
import pt.psoft.g1.psoftg1.common.domain.*;
import pt.psoft.g1.psoftg1.model.Role;
import pt.psoft.g1.psoftg1.service.EventService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@Profile("bootstrap")
public class DatabaseBootstrapper implements CommandLineRunner {
    @Autowired
    private EventService eventService;

    @Override
    public void run(String... args) {
        bootstrapUsers();
        bootstrapGenre();
        bootstrapAuthor();
        bootstrapBook();
        bootstrapLendings();
        bootstrapFines();
    }

    public void bootstrapUsers() {
        final List<CreateUserEvent> users = Arrays.asList(
                new CreateUserEvent( "server", "server", "Andreventura!123",
                        (new Role(Role.ADMIN))),
                new CreateUserEvent( "admin1@mail.com", "Jose Antonio","Andreventura!123",
                        (new Role(Role.ADMIN))),
                new CreateUserEvent( "admin2@mail.com", "Antonio Jose", "Andreventura!123",
                        (new Role(Role.ADMIN))),
                new CreateUserEvent( "librarian1@mail.com",  "Nuno Miguel","Andreventura!123",
                        (new Role(Role.LIBRARIAN))),
                new CreateUserEvent( "librarian2@mail.com", "Miguel Nuno","Andreventura!123",
                        (new Role(Role.LIBRARIAN))),
                new CreateUserEvent( "librarian3@mail.com", "Antonio Pedro","Andreventura!123",
                        (new Role(Role.LIBRARIAN))),
                new CreateUserEvent( "reader1@mail.com","Pedro Antonio","Andreventura!123",
                        (new Role(Role.READER))),
                new CreateUserEvent( "reader2@mail.com", "Ricardo Joao","Andreventura!123",
                        (new Role(Role.READER))),
                new CreateUserEvent( "reader3@mail.com",  "Joao Ricardo","Andreventura!123",
                        (new Role(Role.READER))),
                new CreateUserEvent( "reader4@mail.com", "Luis Pedro","Andreventura!123",
                        (new Role(Role.READER))),
                new CreateUserEvent( "reader5@mail.com", "Pedro Luis", "Andreventura!123",
                        (new Role(Role.READER))),
                new CreateUserEvent( "reader6@mail.com", "Marco Antonio","Andreventura!123",
                        (new Role(Role.READER))),
                new CreateUserEvent( "reader7@mail.com", "Antonio Marco","Andreventura!123",
                        (new Role(Role.READER))),
                new CreateUserEvent( "reader8@mail.com", "Rui Ricardo", "Andreventura!123",
                        (new Role(Role.READER))),
                new CreateUserEvent( "reader9@mail.com",  "Rui Ribeiro","Andreventura!123",
                        (new Role(Role.READER))),
                new CreateUserEvent( "reader10@mail.com", "Luis camoes", "Andreventura!123",
                        (new Role(Role.READER)))
        );
        users.forEach(user -> eventService.send(new Event(EventType.BOOT_USERS, user), ApplicationType.USER));
    }

    public void bootstrapGenre() {
        final List<CreateGenreEvent> genres = Arrays.asList(
                new CreateGenreEvent( "GEN001", "Science Fiction"),
                new CreateGenreEvent( "GEN002", "Fantasy"),
                new CreateGenreEvent( "GEN003", "Mystery"),
                new CreateGenreEvent( "GEN004", "Thriller"),
                new CreateGenreEvent( "GEN005", "Romance"),
                new CreateGenreEvent( "GEN006", "Horror"),
                new CreateGenreEvent( "GEN007", "Non-Fiction"),
                new CreateGenreEvent( "GEN008", "Historical Fiction"),
                new CreateGenreEvent( "GEN009", "Biography"),
                new CreateGenreEvent( "GEN010", "Self-Help")
        );
        genres.forEach(genre -> eventService.send(new Event(EventType.BOOT_GENRES, genre),ApplicationType.BOOK));
    }

    public void bootstrapAuthor() {
        final List<CreateAuthorEvent> authors = Arrays.asList(
                new CreateAuthorEvent( "AUTH001", "Author One", "Bio of Author One", "photoURI1"),
                new CreateAuthorEvent( "AUTH002", "Author Two", "Bio of Author Two", "photoURI2"),
                new CreateAuthorEvent( "AUTH003", "Author Three", "Bio of Author Three", "photoURI3"),
                new CreateAuthorEvent( "AUTH004", "Author Four", "Bio of Author Four", "photoURI4"),
                new CreateAuthorEvent( "AUTH005", "Author Five", "Bio of Author Five", "photoURI5")
        );
        authors.forEach(author -> eventService.send(new Event(EventType.BOOT_AUTHORS, author),ApplicationType.BOOK));
    }

    public void bootstrapBook() {
        final List<CreateBookEvent> books = Arrays.asList(
                new CreateBookEvent( "ISBN001", "How to Peel a Banana", "A comprehensive guide on peeling bananas.", "GEN001", Arrays.asList("AUTH001", "AUTH002"), "photoURI1"),
                new CreateBookEvent( "ISBN002", "The Art of Procrastination", "Mastering the art of doing nothing.", "GEN002", Arrays.asList("AUTH003"), "photoURI2"),
                new CreateBookEvent( "ISBN003", "Why Cats Rule the World", "An in-depth analysis of feline dominance.", "GEN003", Arrays.asList("AUTH004", "AUTH005"), "photoURI3"),
                new CreateBookEvent( "ISBN004", "The Joy of Socks", "Exploring the world of colorful socks.", "GEN004", Arrays.asList("AUTH001", "AUTH003"), "photoURI4"),
                new CreateBookEvent( "ISBN005", "Adventures in Couch Surfing", "Tales from the world of couch surfing.", "GEN005", Arrays.asList("AUTH002", "AUTH004"), "photoURI5"),
                new CreateBookEvent( "ISBN006", "The Secret Life of Plants", "Discovering the hidden world of plants.", "GEN006", Arrays.asList("AUTH001"), "photoURI6"),
                new CreateBookEvent( "ISBN007", "The History of Cheese", "A deep dive into the history of cheese.", "GEN007", Arrays.asList("AUTH002"), "photoURI7"),
                new CreateBookEvent( "ISBN008", "The Art of Napping", "Perfecting the art of taking naps.", "GEN008", Arrays.asList("AUTH003"), "photoURI8"),
                new CreateBookEvent( "ISBN009", "The World of Insects", "Exploring the fascinating world of insects.", "GEN009", Arrays.asList("AUTH004"), "photoURI9"),
                new CreateBookEvent( "ISBN010", "The Joy of Cooking", "A guide to joyful cooking.", "GEN010", Arrays.asList("AUTH005"), "photoURI10"),
                new CreateBookEvent( "ISBN011", "The Science of Sleep", "Understanding the science behind sleep.", "GEN001", Arrays.asList("AUTH001"), "photoURI11"),
                new CreateBookEvent( "ISBN012", "The Art of Gardening", "Mastering the art of gardening.", "GEN002", Arrays.asList("AUTH002"), "photoURI12"),
                new CreateBookEvent( "ISBN013", "The World of Birds", "Exploring the diverse world of birds.", "GEN003", Arrays.asList("AUTH003"), "photoURI13"),
                new CreateBookEvent( "ISBN014", "The History of Chocolate", "A deep dive into the history of chocolate.", "GEN004", Arrays.asList("AUTH004"), "photoURI14"),
                new CreateBookEvent( "ISBN015", "The Joy of Painting", "A guide to joyful painting.", "GEN005", Arrays.asList("AUTH005"), "photoURI15")
        );
        books.forEach(book -> eventService.send(new Event(EventType.BOOT_BOOKS, book),ApplicationType.BOOK));
    }

    public void bootstrapFines() {
        final List<CreateFineEvent> fines = Arrays.asList(
                new CreateFineEvent("FINE001", 100, 500, "LEND001"),
                new CreateFineEvent("FINE002", 150, 750, "LEND002"),
                new CreateFineEvent("FINE003", 200, 1000, "LEND003")
        );
        fines.forEach(fine -> eventService.send(new Event(EventType.FINE_CREATE, fine), ApplicationType.LENDING));
    }

    public void bootstrapLendings() {
        final List<CreateLendingEvent> lendings = Arrays.asList(
                new CreateLendingEvent("ISBN001", null,"reader1@mail.com", "LEND001", LocalDate.now(), LocalDate.now().plusDays(14), null, 100, 1, 14),
                new CreateLendingEvent("ISBN002",null, "reader2@mail.com", "LEND002", LocalDate.now(), LocalDate.now().plusDays(14), null, 150, 2, 14),
                new CreateLendingEvent("ISBN003", null,"reader3@mail.com", "LEND003", LocalDate.now(), LocalDate.now().plusDays(14), null, 200, 3, 14)
        );
        lendings.forEach(lending -> eventService.send(new Event(EventType.LENDING_CREATE, lending), ApplicationType.LENDING));
    }

}
