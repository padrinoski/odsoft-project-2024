package pt.psoft.g1.psoftg1.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.common.Event;

@Service
@Slf4j
public class EventServiceConsumerImpl implements EventServiceConsumer {

    @Autowired
    private EventServiceProducer eventServiceProducer;

    @Override
    public void process(Event event) {

        switch (event.getEventType()) {
            case BOOK_CREATE:
            case BOOK_DELETE:
            case AUTHOR_CREATE:
            case AUTHOR_DELETE:
            case GENRE_CREATE:
            case GENRE_DELETE:
            case BOOT_USERS:
                this.eventServiceProducer.insertEvent(event);
        }

    }



}
