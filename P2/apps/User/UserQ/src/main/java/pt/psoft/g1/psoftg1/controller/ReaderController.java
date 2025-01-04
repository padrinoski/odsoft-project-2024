package pt.psoft.g1.psoftg1.controller;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import pt.psoft.g1.psoftg1.services.ReaderServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readers")
class ReaderController {

    private final ReaderServiceImpl readerService;


}
