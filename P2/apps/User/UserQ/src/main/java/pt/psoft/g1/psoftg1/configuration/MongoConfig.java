package pt.psoft.g1.psoftg1.configuration;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableMongoAuditing(auditorAwareRef = "auditorProviderMongo")
public class MongoConfig {

    /**
     * In case there is no authenticated user, for instance during bootstrapping, we
     * will write SYSTEM
     *
     * @return
     */
    @Bean("auditorProviderMongo")
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .or(() -> Optional.of("SYSTEM"));
    }
}