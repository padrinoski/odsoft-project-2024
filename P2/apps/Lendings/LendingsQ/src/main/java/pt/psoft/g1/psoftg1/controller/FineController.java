package pt.psoft.g1.psoftg1.controller;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.interfaces.FineService;
import pt.psoft.g1.psoftg1.model.FineDTO;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fines")
public class FineController {

    private final FineService lendingService;

    @GetMapping(value = "/{lendingNumber}")
    public ResponseEntity<FineDTO> getFineByLendingNumber(@PathVariable("lendingNumber") final String lendingNumber) {
        final Optional<FineDTO> lending = lendingService.getFineByLendingNumber(lendingNumber);
        if( lending.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Fine not found.");
        else
            return ResponseEntity.ok().body(lending.get());
    }

    @GetMapping
    public ResponseEntity<Iterable<FineDTO>> getAllFines() {
        final var fines = lendingService.getAllFines();
        return ResponseEntity.ok().body( fines );
    }

}
