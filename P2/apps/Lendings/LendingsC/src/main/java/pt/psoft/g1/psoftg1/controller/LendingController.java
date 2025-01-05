package pt.psoft.g1.psoftg1.controller;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.psoft.g1.psoftg1.interfaces.LendingService;
import pt.psoft.g1.psoftg1.model.Lending;
import pt.psoft.g1.psoftg1.model.LendingDTO;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lendings")
public class LendingController {

    private final LendingService lendingService;

    @PostMapping
    public ResponseEntity<LendingDTO> createLending(@RequestBody Lending lending) {
        try {
            return ResponseEntity.ok(lendingService.createLending(lending));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LendingDTO> updateLending(@PathVariable String id, @RequestBody Lending lending) {
        try {
            return ResponseEntity.ok(lendingService.updateLending(id, lending));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLending(@PathVariable String id) {
        try {
            lendingService.deleteLending(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
