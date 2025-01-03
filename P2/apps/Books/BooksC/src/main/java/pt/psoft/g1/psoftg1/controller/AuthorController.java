package pt.psoft.g1.psoftg1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.interfaces.AuthorService;
import pt.psoft.g1.psoftg1.model.Author;
import pt.psoft.g1.psoftg1.model.AuthorDTO;


@Tag(name = "Author", description = "Endpoints for managing Authors")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    
    private final AuthorService service;

    @Operation(summary = "creates a author")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthorDTO> create(@RequestBody Author manager) {
        try {
            final AuthorDTO author = service.create(manager);
            return new ResponseEntity<>(author, HttpStatus.CREATED);
        }
        catch( Exception e ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
        }
    }

    @Operation(summary = "updates a author")
    @PatchMapping(value = "/{authorNumber}")
    public ResponseEntity<AuthorDTO> Update(@PathVariable("authorNumber") final String authorNumber, @RequestBody final Author author) {

        try {
            final AuthorDTO authorDTO = service.updateByAuthorNumber(authorNumber, author);
            return ResponseEntity.ok().body(authorDTO);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }

    @Operation(summary = "deletes a author")
    @DeleteMapping(value = "/{authorNumber}")
    public ResponseEntity<Author> delete(@PathVariable("authorNumber") final String authorNumber ){

        try {
            service.deleteByAuthorNumber(authorNumber);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }

}

