package pt.psoft.g1.psoftg1.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.interfaces.EventServiceConsumer;

@Service
@Transactional
@Slf4j
public class EventServiceConsumerImpl implements EventServiceConsumer {

    @Autowired
    private EventServiceProducerImpl eventServiceProducer;

    @Override
    public void proccess(Event event) {
        switch (event.getEventType()) {
            case USER_CREATE, BOOT_USERS:
                this.eventServiceProducer.insertEvent(event);
            case USER_DELETE:
                this.eventServiceProducer.insertEvent(event);
            case USER_UPDATE:
                this.eventServiceProducer.insertEvent(event);
        }
    }
}
