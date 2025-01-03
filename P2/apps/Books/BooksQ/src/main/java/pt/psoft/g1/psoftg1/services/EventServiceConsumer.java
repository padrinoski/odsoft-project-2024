package pt.psoft.g1.psoftg1.services;

import pt.psoft.g1.psoftg1.common.Event;

public interface EventServiceConsumer {
    void process(Event event);

}
