package pt.psoft.g1.psoftg1;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.testcontainers.shaded.org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.emptyMap;
import static java.util.concurrent.TimeUnit.SECONDS;

class KafkaEventVerifier implements MessageVerifierReceiver<Message<?>> {

    private final Set<Message<?>> consumedEvents = Collections.synchronizedSet(new HashSet<Message<?>>());

    @KafkaListener(topics = "BOOK", groupId = "#{'BOOK_${docker.name}'}")
    void consumeBookTopic(ConsumerRecord<String, String> payload) {
        consumedEvents.add(MessageBuilder.createMessage(payload.value(), new MessageHeaders(emptyMap())));
    }

    @Override
    public Message<?> receive(String destination, long timeout, TimeUnit timeUnit, @Nullable YamlContract contract){
        for (int i = 0; i < timeout; i++) {
            Message<?> msg = consumedEvents.stream().findFirst().orElse(null);
            if (msg != null) {
                return msg;
            }

            try {
                timeUnit.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return consumedEvents.stream().findFirst().orElse(null);
    }

    @Override
    public Message<?> receive(String destination, YamlContract contract) {
        return receive(destination, 5, SECONDS, contract);
    }
}