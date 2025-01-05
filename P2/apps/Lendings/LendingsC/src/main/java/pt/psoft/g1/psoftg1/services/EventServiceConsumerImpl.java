package pt.psoft.g1.psoftg1.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.interfaces.EventServiceConsumer;
import pt.psoft.g1.psoftg1.interfaces.EventServiceProducer;

@Service
@Transactional
@Slf4j
public class EventServiceConsumerImpl implements EventServiceConsumer {

    @Autowired
    private EventServiceProducer eventServiceProducer;

    @Override
    public void proccess(Event event) {
        switch (event.getEventType()) {
            case LENDING_CREATE, BOOT_LENDINGS:
                this.eventServiceProducer.insertEvent(event);
                break;
            case LENDING_DELETE:
                this.eventServiceProducer.insertEvent(event);
                break;
            case LENDING_UPDATE:
                this.eventServiceProducer.insertEvent(event);
                break;
            case FINE_CREATE:
                this.eventServiceProducer.insertEvent(event);
                break;
            case FINE_DELETE:
                this.eventServiceProducer.insertEvent(event);
                break;
            case FINE_UPDATE:
                this.eventServiceProducer.insertEvent(event);
                break;
        }
    }
}
