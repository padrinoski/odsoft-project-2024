package pt.psoft.g1.psoftg1.services;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.common.domain.CreateFineEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteFineEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateFineEvent;
import pt.psoft.g1.psoftg1.interfaces.FineRepository;
import pt.psoft.g1.psoftg1.interfaces.FineService;
import pt.psoft.g1.psoftg1.model.Fine;
import pt.psoft.g1.psoftg1.model.FineDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;

    public FineServiceImpl(FineRepository fineRepository){
        this.fineRepository = fineRepository;
    }
    @Override
    public Optional<FineDTO> getFineByLendingNumber(String lendingNumber) {
        Optional<Fine> fine = fineRepository.getFineByLendingNumber(lendingNumber);
        if(fine.isEmpty()) throw new RuntimeException("No available fine with that LendingID");
        FineDTO fineDTO = fine.get().toDTO();
        return Optional.of(fineDTO);
    }

    @Override
    public Iterable<FineDTO> getAllFines() {
        Iterable<Fine> fines = fineRepository.getAllFines();
        if (!fines.iterator().hasNext()) {
            return Collections.emptyList();
        }
        List<FineDTO> fineDTOs = new ArrayList<>();
        for (Fine fine : fines) {
            fineDTOs.add(fine.toDTO());
        }
        return fineDTOs;
    }

    @Override
    public void createFine(CreateFineEvent event) {
        Fine f = new Fine(event.getLendingId(),event.getFineValuePerDayInCents(),event.getCentsValue());
        fineRepository.save(f);
        log.debug("Persisted fine: " + f);
    }

    @Override
    public void updateFine(UpdateFineEvent event) {
        Optional<Fine> p = fineRepository.findById(event.getPk());
        if (p.isPresent()) {
            Fine fine = p.get();
            fine.setCentsValue(event.getCentsValue());
            fine.setFineValuePerDayInCents(event.getFineValuePerDayInCents());
            fineRepository.save(fine);
        } else {
            log.error("Fine with pk " + event.getPk() + " not found!");
        }
    }

    @Override
    public void deleteFine(DeleteFineEvent event) {
        Optional<Fine> p = fineRepository.findById(event.getPk());
        if (p.isPresent()) {
            Fine fine = p.get();
            fineRepository.delete(fine);
        } else {
            log.error("Fine with pk " + event.getPk() + " not found!");
        }
    }
}
