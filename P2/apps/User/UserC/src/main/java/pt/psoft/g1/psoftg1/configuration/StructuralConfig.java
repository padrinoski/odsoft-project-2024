package pt.psoft.g1.psoftg1.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import pt.psoft.g1.psoftg1.repositories.EventRepository;
import pt.psoft.g1.psoftg1.repositories.EventRepositoryImpl;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator1;


@Configuration
public class StructuralConfig {

    @Value("${data.model}")
    private int dataModel;

    @Value("${id.generator}")
    private int idGenerator;

    private final MongoTemplate mongoTemplate;

    public StructuralConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public IDGenerator getIDGenerator() {
        return new IDGenerator1();
    }


    @Primary
    @Bean
    public EventRepository eventRepository() {
        return new EventRepositoryImpl(mongoTemplate);
    }
}
