package pt.psoft.g1.psoftg1.interfaces;
import pt.psoft.g1.psoftg1.common.ApplicationType;
import pt.psoft.g1.psoftg1.common.Event;

public interface EventServiceProducer {

    Event insertEvent(Event event);

    Event sendEvent(Event event, ApplicationType... appsToNotify);

    void sendMessage(ApplicationType appType, Event msg);

}
