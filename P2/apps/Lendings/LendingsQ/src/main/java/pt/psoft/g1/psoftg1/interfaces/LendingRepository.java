package pt.psoft.g1.psoftg1.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pt.psoft.g1.psoftg1.model.Lending;
import pt.psoft.g1.psoftg1.model.LendingDTO;

import java.util.Optional;
@NoRepositoryBean
public interface LendingRepository extends CrudRepository<Lending, String> {
    Optional<Lending> getLendingByLendingNumber(final String lendingNumber );
    Iterable<Lending> getAllLendings();
    Iterable<Lending> getAllLendingsForABook(final String bookId);

}
