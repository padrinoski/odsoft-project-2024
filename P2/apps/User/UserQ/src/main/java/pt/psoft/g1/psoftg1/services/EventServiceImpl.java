/*
package pt.psoft.g1.psoftg1.services;

import jakarta.transaction.Transactional;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.interfaces.EventService;
import pt.psoft.g1.psoftg1.interfaces.ReaderRepository;
import pt.psoft.g1.psoftg1.interfaces.UserRepository;

public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final ReaderRepository readerRepository;


    @Override
    @Transactional

    public void proccess(Event event) {
        switch (event.getEventType()) {
            case USER_CREATE ->
        }

    }

    private void userCreate(Event event) {
        userRepository.save(event.getUser());
    }

    private void userDelete(Event event) {
        userRepository.delete(event.getUser());
    }

    private void userUpdate(Event event) {
        userRepository.update(event.getUser());
    }
}
*/
