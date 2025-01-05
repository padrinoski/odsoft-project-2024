package pt.psoft.g1.psoftg1.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.common.domain.CreateLendingEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteLendingEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateLendingEvent;
import pt.psoft.g1.psoftg1.interfaces.LendingRepository;
import pt.psoft.g1.psoftg1.interfaces.LendingService;
import pt.psoft.g1.psoftg1.model.Lending;
import pt.psoft.g1.psoftg1.model.LendingDTO;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Log4j2
@Service

public class LendingServiceImpl implements LendingService{

    private final LendingRepository lendingRepository;

    public LendingServiceImpl(LendingRepository lendingRepository) {
        this.lendingRepository = lendingRepository;
    }

    @Override
    public Optional<LendingDTO> getLendingByLendingNumber(String lendingNumber) {
        Optional<Lending> lending = lendingRepository.getLendingByLendingNumber(lendingNumber);
        return lending.map(Lending::toDTO);
    }

    @Override
    public Iterable<LendingDTO> getAllLendings() {
        Iterable<Lending> lendings = lendingRepository.findAll();
        List<LendingDTO> lendingDTOs = new ArrayList<>();
        for (Lending lending : lendings) {
            lendingDTOs.add(lending.toDTO());
        }
        return lendingDTOs;
    }

    @Override
    public Iterable<LendingDTO> getAllLendingsForABook(String bookId) {
        Iterable<Lending> lendings = lendingRepository.getAllLendingsForABook(bookId);
        List<LendingDTO> lendingDTOs = new ArrayList<>();
        for (Lending lending : lendings) {
            lendingDTOs.add(lending.toDTO());
        }
        return lendingDTOs;
    }

    @Override
    public void createLending(CreateLendingEvent event){
        Lending lending = new Lending(event.getBookId(), event.getUserId(),event.getUserEmail(),event.getLendingNumber(), event.getLendingDuration(), event.getFineValuePerDayInCents());
        lendingRepository.save(lending);
        log.debug("Persisted Lending: " + lending);

    }

    @Override
    public void updateLending(UpdateLendingEvent event) {
        Optional<Lending> l = lendingRepository.getLendingByLendingNumber(event.getLendingNumber());
        if (l.isPresent()) {
            Lending lending = l.get();
            int daysOverdue = (int) ChronoUnit.DAYS.between(lending.getLimitDate(), event.getReturnedDate());
            lending.setDaysOverdue(daysOverdue);
            lending.setFineValuePerDayInCents(event.getFineValuePerDayInCents());
            lending.setReturnedDate(event.getReturnedDate());
            lendingRepository.save(lending);
        } else {
            log.error("Lending with lending number " + event.getLendingNumber() + " not found!");
        }
    }

    @Override
    public void deleteLending(DeleteLendingEvent event) {
        Optional<Lending> l = lendingRepository.getLendingByLendingNumber(event.getLendingNumber());
        if (l.isPresent()) {
            Lending lending = l.get();
            lendingRepository.delete(lending);
            log.debug("Deleted Lending: " + lending);
        } else {
            log.error("Lending with lendingNumber " + event.getLendingNumber() + " not found!");
        }
    }
}
