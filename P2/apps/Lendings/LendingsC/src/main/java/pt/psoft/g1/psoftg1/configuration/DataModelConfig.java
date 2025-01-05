package pt.psoft.g1.psoftg1.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import pt.psoft.g1.psoftg1.repositories.EventRepository;
import pt.psoft.g1.psoftg1.repositories.EventRepositoryImpl;
import pt.psoft.g1.psoftg1.shared.IDGenerators.*;


@Configuration
public class DataModelConfig {

    @Value("${data-model}")
    private String dataModel;
    @Value("${id-generator}")
    private String  idGenerator;
    @Bean
    @Primary
    public EventRepository getEventRepository(final MongoTemplate mongoTemplate) {
        return switch (dataModel) {
            case "2" -> new EventRepositoryImpl(mongoTemplate);
            default -> new EventRepositoryImpl(mongoTemplate);
        };
    }


    @Bean
    public IDGenerator getIdGenerator() {
        return switch (idGenerator) {
            case "2" -> new IDGenerator2();
            default -> new IDGenerator1();
        };
    }

}
