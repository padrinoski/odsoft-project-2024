package pt.psoft.g1.psoftg1.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.common.domain.EventMsg;
import pt.psoft.g1.psoftg1.common.utils.EventDeserializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = EventDeserializer.class)
public class Event implements Comparable<Event>{

    private String timestamp;

    private EventType eventType;

    private EventMsg eventData;

    public Event(EventType eventType, EventMsg eventMsg) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
        this.eventType = eventType;
        this.eventData = eventMsg;
    }

    @Override
    public int compareTo(Event o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime thisTimestamp = LocalDateTime.parse(this.timestamp, formatter);
        LocalDateTime otherTimestamp = LocalDateTime.parse(o.timestamp, formatter);

        // Compare in descending order (more recent events first)
        return otherTimestamp.compareTo(thisTimestamp);
    }
}
