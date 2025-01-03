package pt.psoft.g1.psoftg1;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import pt.psoft.g1.psoftg1.common.Event;
import pt.psoft.g1.psoftg1.repositories.EventRepository;
import pt.psoft.g1.psoftg1.repositories.MongoEventRepository;
import pt.psoft.g1.psoftg1.repositories.RedisEventRepository;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator1;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator2;

@Configuration
public class DataModelConfig {

    @Value("${data-model}")
    private String dataModel;
    @Value("${id-generator}")
    private String  idGenerator;
    @Bean
    @Primary
    public EventRepository getEventRepository(final MongoTemplate mongoTemplate, final RedisTemplate<String, Event> redisTemplate) {
        return switch (dataModel) {
            case "2" -> new RedisEventRepository(redisTemplate);
            default -> new MongoEventRepository(mongoTemplate);
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
