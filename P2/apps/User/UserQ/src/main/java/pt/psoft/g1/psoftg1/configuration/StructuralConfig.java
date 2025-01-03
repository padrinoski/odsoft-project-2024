package pt.psoft.g1.psoftg1.configuration;

import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.function.TriFunction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pt.psoft.g1.psoftg1.interfaces.*;
import pt.psoft.g1.psoftg1.repositories.reader.JPAReaderRepositoryService;
import pt.psoft.g1.psoftg1.repositories.user.JPAUserRepositoryService;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator1;
import pt.psoft.g1.psoftg1.shared.IDGenerators.IDGenerator2;
import pt.psoft.g1.psoftg1.shared.repositories.ForbiddenNameRepository;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.shared.infraestructure.repositories.impl.JPAPhotoRepositoryService;
import pt.psoft.g1.psoftg1.shared.infraestructure.repositories.impl.JPAForbiddenNameRepositoryService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StructuralConfig {

    @Value("${data.model}")
    private int dataModel;

    @Value("${id.generator}")
    private int idGenerator;

    private final EntityManager entityManager;

    public StructuralConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public IDGenerator getIDGenerator() {
        return switch (idGenerator) {
            case 1 -> new IDGenerator1();
            case 2 -> new IDGenerator2();
            default -> new IDGenerator1();
        };
    }

    private static final Map<Class<?>, Map<Integer, TriFunction<EntityManager, Class<?>, Class<?>, ?>>> repositoryMappings = new HashMap<>();

    static {
        Map<Integer, TriFunction<EntityManager, Class<?>, Class<?>, ?>> userRepositoryMappings = new HashMap<>();
        userRepositoryMappings.put(2, (entityManager, cls1, cls2) -> new JPAUserRepositoryService(entityManager));
        repositoryMappings.put(UserRepository.class, userRepositoryMappings);

        Map<Integer, TriFunction<EntityManager, Class<?>, Class<?>, ?>> readerRepositoryMappings = new HashMap<>();
        readerRepositoryMappings.put(2, (entityManager, cls1, cls2) -> new JPAReaderRepositoryService(entityManager));
        repositoryMappings.put(ReaderRepository.class, readerRepositoryMappings);

        Map<Integer, TriFunction<EntityManager, Class<?>, Class<?>, ?>> photoRepositoryMappings = new HashMap<>();
        photoRepositoryMappings.put(2, (entityManager, cls1, cls2) -> new JPAPhotoRepositoryService(entityManager));
        repositoryMappings.put(PhotoRepository.class, photoRepositoryMappings);

        Map<Integer, TriFunction<EntityManager, Class<?>, Class<?>, ?>> forbiddenNameRepositoryMappings = new HashMap<>();
        forbiddenNameRepositoryMappings.put(2, (entityManager, cls1, cls2) -> new JPAForbiddenNameRepositoryService(entityManager));
        repositoryMappings.put(ForbiddenNameRepository.class, forbiddenNameRepositoryMappings);
    }

    public <T> T instantiateRepository(Class<T> domainClass) {
        Map<Integer, TriFunction<EntityManager, Class<?>, Class<?>, ?>> mappings = repositoryMappings.get(domainClass);
        if (mappings == null) {
            throw new IllegalArgumentException("No repository mappings found for domain class: " + domainClass.getName());
        }
        TriFunction<EntityManager, Class<?>, Class<?>, ?> triFunction = mappings.getOrDefault(dataModel, mappings.get(2));
        return domainClass.cast(triFunction.apply(entityManager, domainClass, domainClass));
    }

    @Primary
    @Bean
    public UserRepository userRepository() {
        return instantiateRepository(UserRepository.class);
    }

    @Primary
    @Bean
    public ReaderRepository readerRepository() {
        return instantiateRepository(ReaderRepository.class);
    }

    @Primary
    @Bean
    public PhotoRepository photoRepository() {
        return instantiateRepository(PhotoRepository.class);
    }

    @Primary
    @Bean
    public ForbiddenNameRepository forbiddenNameRepository() {
        return instantiateRepository(ForbiddenNameRepository.class);
    }
}