package pt.psoft.g1.psoftg1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.interfaces.BookService;
import pt.psoft.g1.psoftg1.model.Book;
import pt.psoft.g1.psoftg1.model.BookDTO;

@Tag(name = "Books", description = "Endpoints for managing Books")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {


    private final BookService service;


    @Operation(summary = "creates a book")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDTO> create(@RequestBody Book manager) {
        try {
            final BookDTO book = service.create(manager);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        }
        catch( Exception e ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
        }
    }

    @Operation(summary = "updates a book")
    @PatchMapping(value = "/{isbn}")
    public ResponseEntity<BookDTO> Update(@PathVariable("isbn") final String isbn, @RequestBody final Book book) {

        try {
            final BookDTO bookDTO = service.updateByISBN(isbn, book);
            return ResponseEntity.ok().body(bookDTO);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }

    @Operation(summary = "deletes a book")
    @DeleteMapping(value = "/{isbn}")
    public ResponseEntity<Book> delete(@PathVariable("isbn") final String isbn ){

        try {
            service.deleteByISBN(isbn);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }

    }

}

