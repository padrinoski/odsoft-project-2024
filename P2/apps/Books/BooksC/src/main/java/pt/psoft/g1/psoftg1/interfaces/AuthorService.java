package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.model.AuthorDTO;
import pt.psoft.g1.psoftg1.model.Author;



public interface AuthorService {

    AuthorDTO create(final Author book) throws  Exception;
    AuthorDTO updateByAuthorNumber(final String authorNumber, final Author author) throws Exception;
    void deleteByAuthorNumber(final String authorNumber) throws Exception;
}
