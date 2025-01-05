package pt.psoft.g1.psoftg1.services;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.common.ApplicationType;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.EventType;
import pt.psoft.g1.psoftg1.common.domain.CreateLendingEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteLendingEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateLendingEvent;
import pt.psoft.g1.psoftg1.interfaces.EventServiceProducer;
import pt.psoft.g1.psoftg1.interfaces.LendingService;
import pt.psoft.g1.psoftg1.model.Lending;
import pt.psoft.g1.psoftg1.model.LendingDTO;
import pt.psoft.g1.psoftg1.repositories.EventRepository;
import pt.psoft.g1.psoftg1.shared.IDGenerators.*;


@Log4j2
@Service

public class LendingServiceImpl implements LendingService{

    private final IDGenerator idGenerator;
    private final EventRepository eventRepository;
    private final EventServiceProducer eventServiceProducer;

    public LendingServiceImpl(IDGenerator idGenerator, EventRepository eventRepository, EventServiceProducer eventServiceProducer) {
        this.idGenerator = idGenerator;
        this.eventRepository = eventRepository;
        this.eventServiceProducer = eventServiceProducer;
    }

    @Override
    public LendingDTO createLending(Lending lending) throws Exception {
        if (eventRepository.lendingExists(lending.getID())) {
            throw new Exception("Lending with id " + lending.getID() + " already exists!");
        }

        //If needed, communicate with the Book Service to check if the book exists - send event
        //To make easier to implement we are assuming this verification already occurs when the create request for the lending is made

        //If needed, communicate with the User Service to check if the associated reader exists - send event
        //To make easier to implement we are assuming this verification already occurs when the create request for the lending is made

        //If needed, communicate with the User Service to check if the associated librarian exists - send event
        final CreateLendingEvent createLendingEvent = new CreateLendingEvent(lending.getBookId(), lending.getUserId(), lending.getUserEmail(), lending.getLendingNumber(), lending.getStartDate(), lending.getLimitDate(), lending.getReturnedDate(), lending.getFineValuePerDayInCents());
        final Event event = new Event(EventType.LENDING_CREATE, createLendingEvent);

        eventServiceProducer.sendEvent(event, ApplicationType.LENDING);
        log.info("Create Lending: " + createLendingEvent);

        return lending.toDTO();
    }

    @Override
    public LendingDTO updateLending(String id, Lending lending) throws Exception {
        if (!eventRepository.lendingExists(lending.getID())) {
            throw new Exception("Lending with id " + lending.getID() + " not found!");
        }

        final UpdateLendingEvent updateLendingEvent = new UpdateLendingEvent(id, lending.getBookId(), lending.getUserId(), lending.getLendingNumber(), lending.getStartDate(), lending.getLimitDate(), lending.getReturnedDate(), lending.getFineValuePerDayInCents());
        final Event event = new Event(EventType.LENDING_UPDATE, updateLendingEvent);

        eventServiceProducer.sendEvent(event, ApplicationType.LENDING);
        log.info("Update Lending: " + updateLendingEvent);

        return lending.toDTO();
    }

    @Override
    public void deleteLending(String id) throws Exception {
        if (!eventRepository.lendingExists(id)) {
            throw new Exception("Lending with id " + id + " not found!");
        }

        final DeleteLendingEvent deleteLendingEvent = new DeleteLendingEvent(id);
        final Event event = new Event(EventType.LENDING_DELETE, deleteLendingEvent);

        eventServiceProducer.sendEvent(event, ApplicationType.LENDING);
        log.info("Update Lending: " + deleteLendingEvent);
    }
}
