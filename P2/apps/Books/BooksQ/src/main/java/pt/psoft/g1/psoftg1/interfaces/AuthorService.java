package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.common.domain.CreateAuthorEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteAuthorEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateAuthorEvent;
import pt.psoft.g1.psoftg1.model.AuthorDTO;
import pt.psoft.g1.psoftg1.model.Author;
import pt.psoft.g1.psoftg1.model.Author;
import pt.psoft.g1.psoftg1.model.AuthorDTO;

import java.util.Optional;


public interface AuthorService {

    Optional<AuthorDTO> findByAuthorNumber(final String authorNumber);
        Iterable<AuthorDTO> getCatalog();
    void createAuthor(CreateAuthorEvent event);
    void updateAuthor(UpdateAuthorEvent event);
    void deleteAuthor(DeleteAuthorEvent event);
}
