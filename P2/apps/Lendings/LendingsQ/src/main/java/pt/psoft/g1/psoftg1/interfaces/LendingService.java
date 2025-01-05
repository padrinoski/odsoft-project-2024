package pt.psoft.g1.psoftg1.interfaces;


import pt.psoft.g1.psoftg1.common.domain.CreateLendingEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteLendingEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateLendingEvent;
import pt.psoft.g1.psoftg1.model.Lending;
import pt.psoft.g1.psoftg1.model.LendingDTO;

import java.util.List;
import java.util.Optional;


public interface LendingService {
    Optional<LendingDTO> getLendingByLendingNumber( final String lendingNumber );
    Iterable<LendingDTO> getAllLendings();
    Iterable<LendingDTO> getAllLendingsForABook(final String bookId);
    void createLending(CreateLendingEvent event);
    void updateLending(UpdateLendingEvent event);
    void deleteLending(DeleteLendingEvent event);


}
