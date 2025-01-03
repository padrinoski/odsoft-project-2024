package pt.psoft.g1.psoftg1;

import pt.psoft.g1.psoftg1.common.Event;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RedisConfig {


    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.database}")
    private int database;
    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    LettuceConnectionFactory connectionFactory() {
        var redisConfigs = new RedisStandaloneConfiguration(host, port);
        redisConfigs.setDatabase(database);
        return new LettuceConnectionFactory(redisConfigs);
    }

    @Bean
    public RedisTemplate<String, Event> redisTemplate(final RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Event> redisTemplate = new RedisTemplate<>();

        redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
