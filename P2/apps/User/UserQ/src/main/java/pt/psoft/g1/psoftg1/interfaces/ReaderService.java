package pt.psoft.g1.psoftg1.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import pt.psoft.g1.psoftg1.dto.CreateReaderRequest;
import pt.psoft.g1.psoftg1.dto.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.dto.SearchReadersQuery;
import pt.psoft.g1.psoftg1.dto.UpdateReaderRequest;
import pt.psoft.g1.psoftg1.model.reader.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.services.Page;

/**
 *
 */

public interface ReaderService {
    Optional<ReaderDetails> findByUsername(final String username);
    Optional<ReaderDetails> findByReaderNumber(String readerNumber);
    List<ReaderDetails> findByPhoneNumber(String phoneNumber);
    Iterable<ReaderDetails> findAll();
    //List<ReaderDetails> findTopReaders(int minTop);
    //List<ReaderBookCountDTO> findTopByGenre(String genre, LocalDate startDate, LocalDate endDate);
    //Optional<Reader> update(UpdateReaderRequest request) throws Exception;

}
