package pt.psoft.g1.psoftg1.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.domain.*;
import pt.psoft.g1.psoftg1.interfaces.AuthorService;
import pt.psoft.g1.psoftg1.interfaces.BookService;
import pt.psoft.g1.psoftg1.interfaces.GenreService;

@Service
@Slf4j
@AllArgsConstructor

public class EventServiceConsumerImpl implements EventServiceConsumer {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public void process(Event event) {

        switch (event.getEventType()) {
            case BOOK_CREATE, BOOT_BOOKS:
                bookService.createBook((CreateBookEvent) event.getEventData());
                break;
            case BOOK_UPDATE:
                bookService.updateBook((UpdateBookEvent) event.getEventData());
                break;
            case BOOK_DELETE:
                bookService.deleteBook((DeleteBookEvent) event.getEventData());
                break;
            case AUTHOR_CREATE, BOOT_AUTHORS:
                authorService.createAuthor((CreateAuthorEvent) event.getEventData());
                break;
            case AUTHOR_DELETE:
                authorService.deleteAuthor((DeleteAuthorEvent) event.getEventData());
                break;
            case AUTHOR_UPDATE:
                authorService.updateAuthor((UpdateAuthorEvent) event.getEventData());
                break;
            case GENRE_CREATE, BOOT_GENRES:
                genreService.createGenre((CreateGenreEvent) event.getEventData());
                break;
            case GENRE_DELETE:
                genreService.deleteGenre((DeleteGenreEvent) event.getEventData());
                break;
            case GENRE_UPDATE:
                genreService.updateGenre((UpdateGenreEvent) event.getEventData());
                break;
        }

    }



}
