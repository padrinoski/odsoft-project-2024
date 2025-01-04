package pt.psoft.g1.psoftg1.interfaces;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.model.Reader;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 *
 */
@NoRepositoryBean
public interface ReaderRepository extends CrudRepository<Reader, String> {
    List<Reader> findByPhoneNumber(@Param("phoneNumber") @NotNull String phoneNumber);
    Optional<Reader> findByUsername(@Param("username") @NotNull String username);
}
