package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.common.domain.*;
import pt.psoft.g1.psoftg1.dto.ReaderDTO;

import java.util.Optional;

public interface ReaderService {


    Optional<ReaderDTO> findByUsername(final String username);
    Iterable<ReaderDTO> findByPhoneNumber(String phoneNumber);
    Iterable<ReaderDTO> findAll();
    void createReader(CreateReaderEvent event);
    void deleteById(DeleteReaderEvent event);
    void updateReader(UpdateReaderEvent event);
}
