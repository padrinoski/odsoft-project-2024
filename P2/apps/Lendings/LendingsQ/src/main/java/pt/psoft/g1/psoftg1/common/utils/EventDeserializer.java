package pt.psoft.g1.psoftg1.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.common.EventType;
import pt.psoft.g1.psoftg1.common.domain.*;
import pt.psoft.g1.psoftg1.common.domain.DeleteGenreEvent;
import pt.psoft.g1.psoftg1.common.domain.DeleteBookEvent;

import java.io.IOException;

public class EventDeserializer extends JsonDeserializer<Event> {
    @Override
    public Event deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        String eventData = "eventData";
        ObjectNode node = jsonParser.getCodec().readTree(jsonParser);
        String timestamp = node.get("timestamp").asText();
        EventType eventType = EventType.valueOf(node.get("eventType").asText());
        switch (eventType) {
            case BOOT_USERS:
                CreateUserEvent bootstrapUserEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateUserEvent.class);
                return new Event(timestamp, eventType, bootstrapUserEvent);
            case BOOT_BOOKS:
                CreateBookEvent bootstrapBookEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateBookEvent.class);
                return new Event(timestamp, eventType, bootstrapBookEvent);
            case BOOT_AUTHORS:
                CreateAuthorEvent bootstrapAuthorEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateAuthorEvent.class);
                return new Event(timestamp, eventType, bootstrapAuthorEvent);
            case BOOT_GENRES:
                CreateGenreEvent bootstrapGenreEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateGenreEvent.class);
                return new Event(timestamp, eventType, bootstrapGenreEvent);
            case USER_CREATE:
                CreateUserEvent createUserEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateUserEvent.class);
                return new Event(timestamp, eventType, createUserEvent);
            case USER_DELETE:
                DeleteUserEvent deleteUserEvent = jsonParser.getCodec().treeToValue(node.get(eventData), DeleteUserEvent.class);
                return new Event(timestamp, eventType, deleteUserEvent);
            case USER_UPDATE:
                UpdateUserEvent updateUserEvent = jsonParser.getCodec().treeToValue(node.get(eventData), UpdateUserEvent.class);
                return new Event(timestamp, eventType, updateUserEvent);
            case READER_CREATE:
                CreateReaderEvent createReaderEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateReaderEvent.class);
                return new Event(timestamp, eventType, createReaderEvent);
            case READER_DELETE:
                DeleteReaderEvent deleteReaderEvent = jsonParser.getCodec().treeToValue(node.get(eventData), DeleteReaderEvent.class);
                return new Event(timestamp, eventType, deleteReaderEvent);
            case READER_UPDATE:
                UpdateReaderEvent updateReaderEvent = jsonParser.getCodec().treeToValue(node.get(eventData), UpdateReaderEvent.class);
                return new Event(timestamp, eventType, updateReaderEvent);
            case BOOK_CREATE:
                CreateBookEvent createBookEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateBookEvent.class);
                return new Event(timestamp, eventType, createBookEvent);
            case BOOK_DELETE:
                DeleteBookEvent deleteBookEvent = jsonParser.getCodec().treeToValue(node.get(eventData), DeleteBookEvent.class);
                return new Event(timestamp, eventType, deleteBookEvent);
            case AUTHOR_CREATE:
                CreateAuthorEvent createAuthorEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateAuthorEvent.class);
                return new Event(timestamp, eventType, createAuthorEvent);
            case AUTHOR_DELETE:
                DeleteAuthorEvent deleteAuthorEvent = jsonParser.getCodec().treeToValue(node.get(eventData), DeleteAuthorEvent.class);
                return new Event(timestamp, eventType, deleteAuthorEvent);
            case GENRE_CREATE:
                CreateGenreEvent createGenreEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateGenreEvent.class);
                return new Event(timestamp, eventType, createGenreEvent);
            case GENRE_DELETE:
                DeleteGenreEvent deleteGenreEvent = jsonParser.getCodec().treeToValue(node.get(eventData), DeleteGenreEvent.class);
                return new Event(timestamp, eventType, deleteGenreEvent);
            case BOOK_SUGGEST:
                SuggestBookEvent suggestBookEvent = jsonParser.getCodec().treeToValue(node.get(eventData), SuggestBookEvent.class);
                return new Event(timestamp, eventType, suggestBookEvent);
            case BOOK_RECOMMEND:
                RecommendBookEvent recommendBookEvent = jsonParser.getCodec().treeToValue(node.get(eventData), RecommendBookEvent.class);
                return new Event(timestamp, eventType, recommendBookEvent);
            case BOOK_RETURN:
                ReturnBookEvent returnBookEvent = jsonParser.getCodec().treeToValue(node.get(eventData),ReturnBookEvent.class);
                return new Event(timestamp, eventType, returnBookEvent);
            case LENDING_CREATE:
                CreateLendingEvent createLendingEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateLendingEvent.class);
                return new Event(timestamp, eventType, createLendingEvent);
            case LENDING_DELETE:
                DeleteLendingEvent deleteLendingEvent = jsonParser.getCodec().treeToValue(node.get(eventData), DeleteLendingEvent.class);
                return new Event(timestamp, eventType, deleteLendingEvent);
            case LENDING_UPDATE:
                UpdateLendingEvent updateLendingEvent = jsonParser.getCodec().treeToValue(node.get(eventData), UpdateLendingEvent.class);
                return new Event(timestamp, eventType, updateLendingEvent);
            case FINE_CREATE:
                CreateFineEvent createFineEvent = jsonParser.getCodec().treeToValue(node.get(eventData), CreateFineEvent.class);
                return new Event(timestamp, eventType, createFineEvent);
            case FINE_DELETE:
                DeleteFineEvent deleteFineEvent = jsonParser.getCodec().treeToValue(node.get(eventData), DeleteFineEvent.class);
                return new Event(timestamp, eventType, deleteFineEvent);
            case FINE_UPDATE:
                UpdateFineEvent updateFineEvent = jsonParser.getCodec().treeToValue(node.get(eventData), UpdateFineEvent.class);
                return new Event(timestamp, eventType, updateFineEvent);
            default:
                return new Event(timestamp, eventType, null);
        }
    }
}
