package pt.psoft.g1.psoftg1.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.interfaces.BookService;
import pt.psoft.g1.psoftg1.model.BookDTO;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {


    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Iterable<BookDTO>> getCatalog() {
        final var books = bookService.getCatalog();

        return ResponseEntity.ok().body( books );
    }


    @GetMapping(value = "/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable("isbn") final String isbn) {

        final Optional<BookDTO> book = bookService.findByIsbn(isbn);

        if( book.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Book not found.");
        else
            return ResponseEntity.ok().body(book.get());
    }

    @GetMapping(value = "/title/{title}")
    public ResponseEntity<Iterable<BookDTO>> findAllByTitle(@PathVariable("title") final String title){

        final Iterable<BookDTO> books = bookService.findByTitle( title );

        return ResponseEntity.ok().body( books );
    }
    
}

