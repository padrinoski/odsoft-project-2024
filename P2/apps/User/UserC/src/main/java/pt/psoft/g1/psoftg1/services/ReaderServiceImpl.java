package pt.psoft.g1.psoftg1.services;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.common.ApplicationType;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.EventType;
import pt.psoft.g1.psoftg1.common.domain.CreateReaderEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteReaderEvent;
import pt.psoft.g1.psoftg1.dto.ReaderDTO;
import pt.psoft.g1.psoftg1.interfaces.ReaderService;
import pt.psoft.g1.psoftg1.model.Reader;
import pt.psoft.g1.psoftg1.repositories.EventRepositoryImpl;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator;

@Service
@Log4j2
public class ReaderServiceImpl implements ReaderService {

    private final EventRepositoryImpl eventRepository;
    private final EventServiceProducerImpl eventServiceProducer;
    private final IDGenerator idGenerator;


    public ReaderServiceImpl(IDGenerator idGenerator, EventRepositoryImpl eventRepository, EventServiceProducerImpl eventServiceProducer) {
        this.idGenerator = idGenerator;
        this.eventRepository = eventRepository;
        this.eventServiceProducer = eventServiceProducer;
    }

    @Override
    public ReaderDTO create(Reader reader) throws Exception  {
        if(eventRepository.userExists(reader.getID())) {
            throw new Exception("User with ID " + reader.getID() + " already exists!");
        }

        final CreateReaderEvent eventMsg = new CreateReaderEvent(reader.getBirthDate(), reader.getPhoneNumber(), reader.getVersion(), reader.getInterestList());
        final Event event = new Event(EventType.READER_CREATE, eventMsg);
        eventServiceProducer.sendEvent(event, ApplicationType.READER);
        log.info("Create Reader: " + eventMsg);
        return reader.toReaderDTO();
    }

    @Override
    public ReaderDTO update(final String id, final Reader request) throws Exception {
        if(!eventRepository.userExists(id)) {
            throw new Exception("User with ID " + id + " nto found!");
        }

        final CreateReaderEvent eventMsg = new CreateReaderEvent(request.getBirthDate(), request.getPhoneNumber(), request.getVersion(), request.getInterestList());
        final Event event = new Event(EventType.READER_UPDATE, eventMsg);
        eventServiceProducer.sendEvent(event, ApplicationType.READER);
        log.info("Update Reader: " + eventMsg);
        return request.toReaderDTO();
    }

   @Override
    public void removeReader(String readerNumber) throws Exception {
       if(!eventRepository.userExists(readerNumber)) {
           throw new Exception("User with ID " + readerNumber + " nto found!");
       }

       final DeleteReaderEvent eventMsg = new DeleteReaderEvent(readerNumber);
       final Event event = new Event(EventType.READER_DELETE, eventMsg);
       eventServiceProducer.sendEvent(event, ApplicationType.READER);
       log.info("Update Reader: " + eventMsg);
    }
}
