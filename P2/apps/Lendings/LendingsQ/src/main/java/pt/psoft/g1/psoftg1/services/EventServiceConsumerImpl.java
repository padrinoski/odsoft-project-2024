package pt.psoft.g1.psoftg1.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.domain.*;
import pt.psoft.g1.psoftg1.interfaces.EventServiceConsumer;
import pt.psoft.g1.psoftg1.interfaces.EventServiceProducer;
import pt.psoft.g1.psoftg1.interfaces.FineService;
import pt.psoft.g1.psoftg1.interfaces.LendingService;

@Service
@Slf4j
public class EventServiceConsumerImpl implements EventServiceConsumer {

    @Autowired
    private LendingService lendingService;

    @Autowired
    private FineService fineService;

    @Override
    public void proccess(Event event) {
        switch (event.getEventType()) {
            case LENDING_CREATE:
                lendingService.createLending((CreateLendingEvent) event.getEventData());
                break;
            case LENDING_DELETE:
                lendingService.deleteLending((DeleteLendingEvent) event.getEventData());
                break;
            case LENDING_UPDATE:
                lendingService.updateLending((UpdateLendingEvent) event.getEventData());
                break;
            case FINE_CREATE:
                fineService.createFine((CreateFineEvent) event.getEventData());
                break;
            case FINE_DELETE:
                fineService.deleteFine((DeleteFineEvent) event.getEventData());
                break;
            case FINE_UPDATE:
                fineService.updateFine((UpdateFineEvent) event.getEventData());
                break;
        }
    }
}
