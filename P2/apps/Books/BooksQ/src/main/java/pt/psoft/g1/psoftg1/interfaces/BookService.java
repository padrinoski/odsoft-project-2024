package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.common.domain.CreateBookEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteBookEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateBookEvent;
import pt.psoft.g1.psoftg1.model.Book;
import pt.psoft.g1.psoftg1.model.BookDTO;

import java.util.Optional;

public interface BookService {
    Optional<BookDTO> findByIsbn(final String isbn);

    Optional<Book> getBookByIsbn( final String isbn );

    Iterable<BookDTO> findByTitle(final String title);

    Iterable<BookDTO> getCatalog();
    void createBook(CreateBookEvent event);
    void updateBook(UpdateBookEvent event);
    void deleteBook(DeleteBookEvent event);

}
