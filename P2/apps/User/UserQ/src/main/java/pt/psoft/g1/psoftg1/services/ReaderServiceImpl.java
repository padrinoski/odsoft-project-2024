package pt.psoft.g1.psoftg1.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import pt.psoft.g1.psoftg1.common.domain.CreateReaderEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteReaderEvent;
import pt.psoft.g1.psoftg1.common.domain.UpdateReaderEvent;
import pt.psoft.g1.psoftg1.dto.ReaderDTO;
import pt.psoft.g1.psoftg1.interfaces.ReaderRepository;
import pt.psoft.g1.psoftg1.interfaces.ReaderService;
import pt.psoft.g1.psoftg1.model.Reader;
import pt.psoft.g1.psoftg1.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository readerRepository;


    public ReaderServiceImpl( ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @Override
    public Optional<ReaderDTO> findByUsername(String username) {
        Optional<Reader> reader = readerRepository.findByUsername(username);
        return reader.map(r -> new ReaderDTO(r.getUsername(), r.getPassword(), r.getName(), r.getBirthDate(), r.getPhoneNumber(), r.getInterestList()));
    }

    @Override
    public Iterable<ReaderDTO> findByPhoneNumber(String phoneNumber) {
        Iterable<Reader> readers = readerRepository.findByPhoneNumber(phoneNumber);
        List<ReaderDTO> readerDTOs = new ArrayList<>();
        for (Reader reader : readers) {
            readerDTOs.add(new ReaderDTO(reader.getUsername(), reader.getPassword(), reader.getName(), reader.getBirthDate(), reader.getPhoneNumber(), reader.getInterestList()));
        }
        return readerDTOs;
    }

    @Override
    public Iterable<ReaderDTO> findAll() {
        Iterable<Reader> readers = readerRepository.findAll();
        List<ReaderDTO> readerDTOs = new ArrayList<>();
        for (Reader reader : readers) {
            readerDTOs.add(new ReaderDTO(reader.getUsername(), reader.getPassword(), reader.getName(), reader.getBirthDate(), reader.getPhoneNumber(), reader.getInterestList()));
        }
        return readerDTOs;
    }


    @Override
    public void createReader(CreateReaderEvent event) {
        Reader reader = new Reader(event.getUsername(), event.getPassword(), event.getName(), event.getBirthDate(), event.getPhoneNumber(), event.getInterestList());
        readerRepository.save(reader);
        log.debug("Persisted reader: " + reader);
    }

    @Override
    public void deleteById(DeleteReaderEvent event) {
        Optional<Reader> optReader = readerRepository.findById(event.getId());
        if (optReader.isPresent()) {
            readerRepository.delete(optReader.get());
        } else {
            log.error("User with id " + event.getId() + " not found");
        }

    }

    @Override
    public void updateReader(UpdateReaderEvent event) {
        Optional<Reader> optReader = readerRepository.findById(event.getReaderId());
        if (optReader.isPresent()) {
            Reader reader = optReader.get();
            reader.setName(event.getName());
            reader.setUsername(event.getUsername());
            reader.setPassword(event.getPassword());
            reader.setBirthDate(event.getBirthDate());
            reader.setPhoneNumber(event.getPhoneNumber());
            reader.setInterestList(event.getInterestList());
            readerRepository.save(reader);
        } else {
            log.error("User with id " + event.getReaderId() + " not found");
        }
    }
}
