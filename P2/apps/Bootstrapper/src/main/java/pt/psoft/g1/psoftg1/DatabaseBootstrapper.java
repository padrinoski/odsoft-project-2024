package pt.psoft.g1.psoftg1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.common.ApplicationType;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.EventType;
import pt.psoft.g1.psoftg1.common.domain.CreateAuthorEvent;
import pt.psoft.g1.psoftg1.common.domain.CreateBookEvent;
import pt.psoft.g1.psoftg1.common.domain.CreateGenreEvent;
import pt.psoft.g1.psoftg1.common.domain.BootstrapUserEvent;
import pt.psoft.g1.psoftg1.service.EventService;

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
    }

    public void bootstrapUsers() {
        final List<BootstrapUserEvent> users = Arrays.asList(
                new BootstrapUserEvent((long) -1, "server", "server", "server", Set.of("Server"),"server", "server"),
                new BootstrapUserEvent((long) 1, "admin1@mail.com", "AdminPW1", "Jose Antonio", Set.of("ADMIN"),"355489123", "Rua Um"),
                new BootstrapUserEvent((long) 2, "admin2@mail.com", "AdminPW2", "Antonio Jose", Set.of("ADMIN"),"321984553", "Rua dois"),
                new BootstrapUserEvent((long) 3, "librarian1@mail.com", "librarian1", "Nuno Miguel",Set.of("LIBRARIAN"), "253647883", "Rua tres"),
                new BootstrapUserEvent((long) 4, "librarian2@mail.com", "librarian2", "Miguel Nuno",Set.of("LIBRARIAN"), "253698854", "Rua quatro"),
                new BootstrapUserEvent((long) 5, "librarian3@mail.com", "librarian3", "Antonio Pedro",Set.of("LIBRARIAN"), "254148863", "Rua vinte"),
                new BootstrapUserEvent((long) 6, "reader1@mail.com", "reader1", "Pedro Antonio",Set.of("READER"), "452369871", "Rua cinco"),
                new BootstrapUserEvent((long) 7, "reader2@mail.com", "reader2", "Ricardo Joao",Set.of("READER"), "452858596", "Rua seis"),
                new BootstrapUserEvent((long) 8, "reader3@mail.com", "reader3", "Joao Ricardo",Set.of("READER"), "425364781", "Rua sete"),
                new BootstrapUserEvent((long) 9, "reader4@mail.com", "reader4", "Luis Pedro",Set.of("READER"), "526397747", "Rua oito"),
                new BootstrapUserEvent((long) 10, "reader5@mail.com", "reader5", "Pedro Luis",Set.of("READER"), "523689471", "Rua nove "),
                new BootstrapUserEvent((long) 11, "reader6@mail.com", "reader6", "Marco Antonio",Set.of("READER"), "253148965", "Rua dez"),
                new BootstrapUserEvent((long) 12, "reader7@mail.com", "reader7", "Antonio Marco",Set.of("READER"), "201023056", "Rua onze"),
                new BootstrapUserEvent((long) 13, "reader8@mail.com", "reader8", "Rui Ricardo",Set.of("READER"), "748526326", "Rua doze"),
                new BootstrapUserEvent((long) 14, "reader9@mail.com", "reader9", "Rui Ribeiro", Set.of("READER"),"748526327", "Rua catorze"),
                new BootstrapUserEvent((long) 15, "reader10@mail.com", "reader10", "Luis camoes", Set.of("READER"),"748526328", "Rua quinze")
        );
        users.forEach(user -> eventService.send(new Event(EventType.BOOT_USERS, user), ApplicationType.USER, ApplicationType.BOOK, ApplicationType.LENDING, ApplicationType.RECOMMENDATION));
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
        genres.forEach(genre -> eventService.send(new Event(EventType.BOOT_GENRES, genre), ApplicationType.USER,ApplicationType.BOOK, ApplicationType.LENDING, ApplicationType.RECOMMENDATION));
    }

    public void bootstrapAuthor() {
        final List<CreateAuthorEvent> authors = Arrays.asList(
                new CreateAuthorEvent( "AUTH001", "Author One", "Bio of Author One", "photoURI1"),
                new CreateAuthorEvent( "AUTH002", "Author Two", "Bio of Author Two", "photoURI2"),
                new CreateAuthorEvent( "AUTH003", "Author Three", "Bio of Author Three", "photoURI3"),
                new CreateAuthorEvent( "AUTH004", "Author Four", "Bio of Author Four", "photoURI4"),
                new CreateAuthorEvent( "AUTH005", "Author Five", "Bio of Author Five", "photoURI5")
        );
        authors.forEach(author -> eventService.send(new Event(EventType.BOOT_AUTHORS, author), ApplicationType.USER,ApplicationType.BOOK, ApplicationType.LENDING, ApplicationType.RECOMMENDATION));
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
        books.forEach(book -> eventService.send(new Event(EventType.BOOT_BOOKS, book), ApplicationType.USER,ApplicationType.BOOK, ApplicationType.LENDING, ApplicationType.RECOMMENDATION));
    }

}
