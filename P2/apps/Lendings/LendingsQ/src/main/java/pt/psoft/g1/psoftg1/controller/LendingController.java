package pt.psoft.g1.psoftg1.controller;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.interfaces.LendingService;
import pt.psoft.g1.psoftg1.model.Lending;
import pt.psoft.g1.psoftg1.model.LendingDTO;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lendings")
public class LendingController {

    private final LendingService lendingService;

    @GetMapping(value = "/{lendingNumber}")
    public ResponseEntity<LendingDTO> getLendingByLendingNumber(@PathVariable("lendingNumber") final String lendingNumber) {
        final Optional<LendingDTO> lending = lendingService.getLendingByLendingNumber(lendingNumber);
        if( lending.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Lending not found.");
        else
            return ResponseEntity.ok().body(lending.get());
    }

    @GetMapping
    public ResponseEntity<Iterable<LendingDTO>> getAllLendings() {
        final var lendings = lendingService.getAllLendings();
        return ResponseEntity.ok().body( lendings );
    }


    @GetMapping(value = "/{bookId}")
    public ResponseEntity<Iterable<LendingDTO>> getAllLendingsForABook(@PathVariable("bookId") final String bookId) {
        final var lendings = lendingService.getAllLendingsForABook(bookId);
        return ResponseEntity.ok().body( lendings );
    }


}
