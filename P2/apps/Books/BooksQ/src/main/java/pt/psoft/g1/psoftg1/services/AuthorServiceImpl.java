package pt.psoft.g1.psoftg1.services;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.common.domain.CreateAuthorEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteAuthorEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateAuthorEvent;
import pt.psoft.g1.psoftg1.interfaces.AuthorRepository;
import pt.psoft.g1.psoftg1.interfaces.AuthorService;
import pt.psoft.g1.psoftg1.interfaces.BookRepository;
import pt.psoft.g1.psoftg1.interfaces.GenreRepository;
import pt.psoft.g1.psoftg1.model.Author;
import pt.psoft.g1.psoftg1.model.AuthorDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Log4j2
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    public AuthorServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }
    @Override
    public Optional<AuthorDTO> findByAuthorNumber(String authorNumber) {
        Optional<Author> author = authorRepository.findByAuthorNumber(authorNumber);
        return author.map(a -> new AuthorDTO(a.getAuthorNumber(), a.getName(), a.getBio(), null));
    }

    @Override
    public Iterable<AuthorDTO> getCatalog() {
        Iterable<Author> authors = authorRepository.findAll();
        List<AuthorDTO> authorDTOs = new ArrayList<>();
        for (Author author : authors) {
            authorDTOs.add(new AuthorDTO(author.getAuthorNumber(), author.getName(), author.getBio(), null));
        }
        return authorDTOs;
    }

    @Override
    public void createAuthor(CreateAuthorEvent event) {
        Author author = new Author(event.getAuthorNumber(), event.getName(), event.getBio(), null);
        authorRepository.save(author);
        log.debug("Persisted author: " + author);

    }

    @Override
    public void updateAuthor(UpdateAuthorEvent event) {
        Optional<Author> optionalAuthor = authorRepository.findByAuthorNumber(event.getAuthorNumber());
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            author.setName(event.getName());
            author.setBio(event.getBio());
            authorRepository.save(author);
        } else {
            log.error("Author with authorNumber " + event.getAuthorNumber() + " not found!");
        }
    }

    @Override
    public void deleteAuthor(DeleteAuthorEvent event) {
        Optional<Author> optionalAuthor = authorRepository.findByAuthorNumber(event.getAuthorNumber());
        if (optionalAuthor.isPresent()) {
            authorRepository.delete(optionalAuthor.get());
        } else {
            log.error("Author with authorNumber " + event.getAuthorNumber() + " not found!");
        }
    }
}

