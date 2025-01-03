package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.common.domain.CreateGenreEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteGenreEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateGenreEvent;
import pt.psoft.g1.psoftg1.model.Genre;

import java.util.Optional;


public interface GenreService {
    Optional<Genre> findByPk(final String pk);

    Optional<Genre> findByGenreName(final String genreName);

    Iterable<Genre> getCatalog();
    void createGenre(CreateGenreEvent event);
    void updateGenre(UpdateGenreEvent event);
    void deleteGenre(DeleteGenreEvent event);
}
