package pt.psoft.g1.psoftg1.controller;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pt.psoft.g1.psoftg1.dto.ReaderDTO;
import pt.psoft.g1.psoftg1.interfaces.ReaderService;
import pt.psoft.g1.psoftg1.model.Reader;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readers")
class ReaderController {

    private final ReaderService readerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReaderDTO> createReader(@RequestBody Reader request) {
        try {
            final ReaderDTO reader = readerService.create(request);
            return new ResponseEntity<>(reader, HttpStatus.CREATED);
        }
        catch( Exception e ) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,e.getMessage());
        }

    }

    @PatchMapping
    public ResponseEntity<ReaderDTO> updateReader(final String id, Reader request) {
        try {
            final ReaderDTO user = readerService.update(id, request);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }

    @DeleteMapping("")
    public ResponseEntity<ReaderDTO> deleteReader(@RequestParam final String readerNumber) {
        try {
            readerService.removeReader(readerNumber);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
  }
}
