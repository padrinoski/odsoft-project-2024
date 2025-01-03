package pt.psoft.g1.psoftg1.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.common.domain.CreateGenreEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteGenreEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateGenreEvent;
import pt.psoft.g1.psoftg1.interfaces.AuthorRepository;
import pt.psoft.g1.psoftg1.interfaces.BookRepository;
import pt.psoft.g1.psoftg1.interfaces.GenreRepository;
import pt.psoft.g1.psoftg1.interfaces.GenreService;
import pt.psoft.g1.psoftg1.model.Book;
import pt.psoft.g1.psoftg1.model.Genre;
import pt.psoft.g1.psoftg1.model.GenreDTO;

import java.util.Optional;

@Service
@Log4j2
public class GenreServiceImpl implements GenreService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    public GenreServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<Genre> findByPk(String pk) {
        return genreRepository.findByPk(pk);
    }

    @Override
    public Optional<Genre> findByGenreName(String genreName) {
        return genreRepository.findByGenre(genreName);
    }

    @Override
    public Iterable<Genre> getCatalog() {
        return genreRepository.findAll();
    }

    @Override
    public void createGenre(CreateGenreEvent event) {
        Genre g = new Genre(event.getGenre());
        g.setPk(event.getPk());

        genreRepository.save(g);
        log.debug("Persisted genre: " + g);

    }

    @Override
    public void updateGenre(UpdateGenreEvent event) {
        Optional<Genre> g = genreRepository.findByPk(event.getPk());
        if (g.isPresent()) {
            Genre genre = g.get();
            genre.setGenre(event.getGenre());
            genreRepository.save(genre);
        } else {
            log.error("Genre with pk " + event.getPk() + " not found!");
        }
    }

    @Override
    public void deleteGenre(DeleteGenreEvent event) {
        Optional<Genre> g = genreRepository.findByPk(event.getPk());
        if (g.isPresent()) {
            Genre genre = g.get();
            genreRepository.delete(genre);
        } else {
            log.error("Genre with pk " + event.getPk() + " not found!");
        }
    }
}
