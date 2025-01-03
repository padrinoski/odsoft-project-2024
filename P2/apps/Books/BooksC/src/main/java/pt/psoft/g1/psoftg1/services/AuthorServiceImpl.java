package pt.psoft.g1.psoftg1.services;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.interfaces.AuthorService;
import pt.psoft.g1.psoftg1.model.Author;
import pt.psoft.g1.psoftg1.model.AuthorDTO;


@Service
@Log4j2
public class AuthorServiceImpl implements AuthorService {

    @Override
    public AuthorDTO create(Author book) throws Exception {
        return null;
    }

    @Override
    public AuthorDTO updateByAuthorNumber(String authorNumber, Author author) throws Exception {
        return null;
    }

    @Override
    public void deleteByAuthorNumber(String authorNumber) throws Exception {

    }
}

