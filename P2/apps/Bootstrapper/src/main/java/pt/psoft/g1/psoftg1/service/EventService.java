package pt.psoft.g1.psoftg1.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import pt.psoft.g1.psoftg1.common.ApplicationType;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static pt.psoft.g1.psoftg1.common.utils.ObjectToJsonConverter.convertObjectToJson;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public Event send(Event event, ApplicationType... appsToNotify) {
        Arrays.stream(appsToNotify).forEach(app -> sendMessage(app, event));
        return eventRepository.insert(event);
    }


    private void sendMessage(ApplicationType appType, Event msg) {
        try {
            final String json = convertObjectToJson(msg);
            kafkaTemplate.send(appType.toString(), json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

