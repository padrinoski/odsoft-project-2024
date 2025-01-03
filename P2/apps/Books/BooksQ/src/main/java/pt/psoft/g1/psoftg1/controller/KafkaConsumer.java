package pt.psoft.g1.psoftg1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.services.EventServiceConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaConsumer {

    private final EventServiceConsumer service;

    @KafkaListener(topics = "BOOK", groupId = "#{'BOOK_${docker.name}'}")
    @Transactional("kafkaTransactionManager")
    public void listen(String message) {
        try {
            log.debug("REACHED LISTEN");
            service.process(new ObjectMapper().readValue(message, Event.class));
        } catch (JsonProcessingException e) {
            log.warn("Error converting event message to event object, for this message: " + message
                    + "\nError message: " + e.getMessage());
        }
    }
}
