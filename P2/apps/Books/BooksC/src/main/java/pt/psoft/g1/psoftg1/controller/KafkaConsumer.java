package pt.psoft.g1.psoftg1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.services.EventServiceConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class KafkaConsumer {


    @Autowired
    private EventServiceConsumer service;

    @KafkaListener(topics = "BOOK", groupId = "#{'BOOK_${docker.name}'}")
    @Transactional
    public void listen(String message) {
        try {
            service.process(new ObjectMapper().readValue(message, Event.class));
        } catch (JsonProcessingException e) {
            log.warn("Error converting event message to event object, for this message: " + message
                    + "\nError message: " + e.getMessage());
        }

    }

}
