package pt.psoft.g1.psoftg1.configuration;

import org.neo4j.driver.Driver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
public class Neo4JConfig {

    @Bean
    public Neo4jClient neo4jClient(Driver driver) {
        return Neo4jClient.create(driver);
    }
    @Bean
    public Neo4jTemplate neo4jTemplate(Neo4jClient neo4jClient, Neo4jMappingContext mappingContext) {
        return new Neo4jTemplate(neo4jClient, mappingContext);
    }

    @Bean
    public PlatformTransactionManager transactionManager(Driver driver, DatabaseSelectionProvider provider) {
        return new Neo4jTransactionManager(driver, provider);
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
