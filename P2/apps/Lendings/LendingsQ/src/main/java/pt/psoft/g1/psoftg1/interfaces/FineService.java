package pt.psoft.g1.psoftg1.interfaces;

import pt.psoft.g1.psoftg1.common.domain.CreateFineEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteFineEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateFineEvent;
import pt.psoft.g1.psoftg1.model.FineDTO;

import java.util.Optional;

public interface FineService {

    Optional<FineDTO> getFineByLendingNumber(final String lendingNumber );
    Iterable<FineDTO> getAllFines();

    void createFine(CreateFineEvent event);
    void updateFine(UpdateFineEvent event);
    void deleteFine(DeleteFineEvent event);

}
