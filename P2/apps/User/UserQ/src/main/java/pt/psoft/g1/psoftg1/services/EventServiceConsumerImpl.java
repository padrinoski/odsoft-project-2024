package pt.psoft.g1.psoftg1.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.domain.*;
import pt.psoft.g1.psoftg1.interfaces.EventServiceConsumer;
import pt.psoft.g1.psoftg1.interfaces.ReaderService;
import pt.psoft.g1.psoftg1.interfaces.UserService;

@Service
@Slf4j
@AllArgsConstructor

public class EventServiceConsumerImpl implements EventServiceConsumer {
    private final UserService userService;
    private final ReaderService readerService;

    @Override
    public void proccess(Event event) {
        switch (event.getEventType()) {
            case USER_CREATE, BOOT_USERS:
                userService.createUser((CreateUserEvent) event.getEventData());
                break;
            case USER_DELETE:
                userService.deleteById((DeleteUserEvent) event.getEventData());
                break;
            case USER_UPDATE:
                userService.updateUser((UpdateUserEvent) event.getEventData());
                break;
            case READER_CREATE:
                readerService.createReader((CreateReaderEvent) event.getEventData());
                break;
            case READER_DELETE:
                readerService.deleteById((DeleteReaderEvent) event.getEventData());
                break;
            case READER_UPDATE:
                readerService.updateReader((UpdateReaderEvent) event.getEventData());
                break;
        }
    }
}
