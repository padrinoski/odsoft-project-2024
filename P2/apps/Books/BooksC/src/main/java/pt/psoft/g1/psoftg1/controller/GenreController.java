package pt.psoft.g1.psoftg1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.interfaces.GenreService;
import pt.psoft.g1.psoftg1.model.Genre;
import pt.psoft.g1.psoftg1.model.GenreDTO;

@Tag(name = "Genres", description = "Endpoints for managing Genres")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService service;

    @Operation(summary = "creates a genre")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenreDTO> create(@RequestBody Genre manager) {
        try {
            final GenreDTO genre = service.create(manager);
            return new ResponseEntity<>(genre, HttpStatus.CREATED);
        }
        catch( Exception e ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
        }
    }

    @Operation(summary = "updates a genre")
    @PatchMapping(value = "/{pk}")
    public ResponseEntity<GenreDTO> Update(@PathVariable("pk") final String pk, @RequestBody final Genre genre) {

        try {
            final GenreDTO genreDTO = service.updateByPK(pk, genre);
            return ResponseEntity.ok().body(genreDTO);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }

    @Operation(summary = "deletes a genre")
    @DeleteMapping(value = "/{pk}")
    public ResponseEntity<Genre> delete(@PathVariable("pk") final String pk ){

        try {
            service.deleteByPK(pk);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }

}
