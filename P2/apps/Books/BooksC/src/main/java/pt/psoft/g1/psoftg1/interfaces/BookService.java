package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.model.Book;
import pt.psoft.g1.psoftg1.model.BookDTO;

public interface BookService {
    BookDTO create(final Book book) throws  Exception;
    BookDTO updateByISBN(final String isbn, final Book book) throws Exception;
    void deleteByISBN(final String isbn) throws Exception;

}
