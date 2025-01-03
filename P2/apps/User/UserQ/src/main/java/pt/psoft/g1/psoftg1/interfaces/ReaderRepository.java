package pt.psoft.g1.psoftg1.interfaces;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import pt.psoft.g1.psoftg1.dto.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.dto.SearchReadersQuery;
import pt.psoft.g1.psoftg1.model.reader.ReaderDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@NoRepositoryBean
public interface ReaderRepository {
    Optional<ReaderDetails> findByReaderNumber(@Param("readerNumber") @NotNull String readerNumber);
    List<ReaderDetails> findByPhoneNumber(@Param("phoneNumber") @NotNull String phoneNumber);
    Optional<ReaderDetails> findByUsername(@Param("username") @NotNull String username);
    Optional<ReaderDetails> findByUserId(@Param("userId") @NotNull String userId);
    int getCountFromCurrentYear();
    Iterable<ReaderDetails> findAll();
    // todo
    //Page<ReaderDetails> findTopReaders(Pageable pageable);
    //Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable, String genre, LocalDate startDate, LocalDate endDate);
    List<ReaderDetails> searchReaderDetails(pt.psoft.g1.psoftg1.shared.services.Page page, SearchReadersQuery query);

}
