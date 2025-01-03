package pt.psoft.g1.psoftg1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.interfaces.AuthorService;
import pt.psoft.g1.psoftg1.model.AuthorDTO;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<Iterable<AuthorDTO>> getCatalog() {
        final var authors = authorService.getCatalog();

        return ResponseEntity.ok().body( authors );
    }


    @GetMapping(value = "/{authorNumber}")
    public ResponseEntity<AuthorDTO> getAuthorByAuthorNumber(@PathVariable("authorNumber") final String authorNumber) {

        final Optional<AuthorDTO> author = authorService.findByAuthorNumber(authorNumber);

        if( author.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Author not found.");
        else
            return ResponseEntity.ok().body(author.get());
    }

}

