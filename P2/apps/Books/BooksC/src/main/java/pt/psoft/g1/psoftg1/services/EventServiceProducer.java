package pt.psoft.g1.psoftg1.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.common.ApplicationType;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.repositories.EventRepository;

import java.util.Arrays;

import static pt.psoft.g1.psoftg1.common.utils.ObjectToJsonConverter.convertObjectToJson;

@Service
public class EventServiceProducer {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Event insertEvent(Event event) {
        if (event != null) {
            return eventRepository.save(event);
        }
        return null;
    }

    public Event send(Event event, ApplicationType... appsToNotify) {
        Arrays.stream(appsToNotify).forEach(app -> sendMessage(app, event));
        return event;
    }


    public void sendMessage(ApplicationType appType, Event msg) {
        try {
            final String json = convertObjectToJson(msg);
            kafkaTemplate.send(appType.toString(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
