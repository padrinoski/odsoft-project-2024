package pt.psoft.g1.psoftg1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.interfaces.GenreService;
import pt.psoft.g1.psoftg1.model.GenreDTO;
import pt.psoft.g1.psoftg1.model.Genre;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<Iterable<GenreDTO>> getCatalog() {
        final var genres = genreService.getCatalog();

        final var genreDTOs = new ArrayList<GenreDTO>();
        for (Genre genre : genres) {
            genreDTOs.add(genre.toDTO());
        }

        return ResponseEntity.ok().body( genreDTOs );
    }


    @GetMapping(value = "/{pk}")
    public ResponseEntity<GenreDTO> getGenreByPk(@PathVariable("pk") final String pk) {

        final Optional<Genre> genre = genreService.findByPk(pk);

        if( genre.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Genre not found.");
        else
            return ResponseEntity.ok().body(genre.get().toDTO());
    }

    @GetMapping(value = "/genreName/{genreName}")
    public ResponseEntity<GenreDTO> getGenreByName(@PathVariable("genreName") final String genreName) {

        final Optional<Genre> genre = genreService.findByGenreName(genreName);

        if( genre.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Genre not found.");
        else
            return ResponseEntity.ok().body(genre.get().toDTO());
    }

}
