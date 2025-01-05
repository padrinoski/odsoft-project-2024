package pt.psoft.g1.psoftg1.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pt.psoft.g1.psoftg1.model.Fine;
import pt.psoft.g1.psoftg1.model.FineDTO;

import java.util.Optional;

@NoRepositoryBean
public interface FineRepository extends CrudRepository<Fine, String> {
    Optional<Fine> getFineByLendingNumber(final String lendingNumber );
    Iterable<Fine> getAllFines();
}
