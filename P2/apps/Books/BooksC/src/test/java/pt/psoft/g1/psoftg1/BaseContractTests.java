package pt.psoft.g1.psoftg1;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.checkerframework.checker.nullness.qual.Nullable;
import org.testcontainers.utility.DockerImageName;

import pt.psoft.g1.psoftg1.interfaces.BookService;
import pt.psoft.g1.psoftg1.model.Author;
import pt.psoft.g1.psoftg1.model.Book;
import pt.psoft.g1.psoftg1.model.Genre;


import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = BooksCommandApplication.class)
@ComponentScan(basePackages = "pt.psoft.g1.psoftg1.services")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Testcontainers
@AutoConfigureMessageVerifier
@ActiveProfiles("test")
public abstract class BaseContractTests {

//    @Container
//    static KafkaContainer kafka = new KafkaContainer();

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.5.0"));

    @Autowired
    private BookService bookService;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        kafka.start();
        registry.add("spring.kafka.bootstrap-servers", () -> kafka.getBootstrapServers());
//        registry.add("spring.data.mongodb.host", () -> "db-booksc-1");
//        registry.add("spring.data.mongodb.database", () -> "events");
//        registry.add("spring.data.mongodb.port", () -> "27017");
//        registry.add("spring.data.mongodb.username", () -> "user");
//        registry.add("spring.data.mongodb.password", () -> "pass");
    }

    @Test
    public void createOrderTest() {
        Genre genre = new Genre("Science Fiction");
        Author aut1 = new Author( "AUTH001", "Author One", "Bio of Author One", "photoURI1");
        Author aut2 =  new Author( "AUTH002", "Author Two", "Bio of Author Two", "photoURI2");
        List<Author> authors = Arrays.asList(aut1, aut2);
        Book book = new Book( "ISBN001", "How to Peel a Banana", "A comprehensive guide on peeling bananas.", genre, authors, "photoURI1");
        try{
            bookService.create(book);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertNotNull("Order creation should succeed", bookService);
    }

    @EnableKafka
    @Configuration
    static class TestConfig {

        @Bean
        KafkaMessageVerifier  kafkaTemplateMessageVerifier() {
            return new KafkaMessageVerifier();
        }

    }

    static class KafkaMessageVerifier  implements MessageVerifierReceiver<Message<?>> {

        private final Set<Message<?>> consumedEvents = Collections.synchronizedSet(new HashSet<>());
        private static final Log LOG = LogFactory.getLog(KafkaMessageVerifier .class);

        Map<String, BlockingQueue<Message<?>>> broker = new ConcurrentHashMap<>();

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
            return receive(destination, 15, TimeUnit.SECONDS, contract);
        }

        @KafkaListener(topics = "BOOK")
        void consumeBookTopic(ConsumerRecord<String, String> payload,@Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
            LOG.info("Got a message from a topic [" + topic + "]");
            Map<String, Object> headers = new HashMap<>();
            new DefaultKafkaHeaderMapper().toHeaders(payload.headers(), headers);
            broker.putIfAbsent(topic, new ArrayBlockingQueue<>(1));
            BlockingQueue<Message<?>> messageQueue = broker.get(topic);
            messageQueue.add(MessageBuilder.createMessage(payload.value(), new MessageHeaders(headers)));        }

    }
}
