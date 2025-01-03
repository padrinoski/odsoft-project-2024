package pt.psoft.g1.psoftg1.repositories;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import pt.psoft.g1.psoftg1.interfaces.GetID;

public class JPABaseRepository <T extends GetID<ID>,ID> extends SimpleJpaRepository<T, ID> {

    private final Class<T> domainClass;

    private final String tableName;

    public JPABaseRepository(Class<T> domainClass, String tableName, EntityManager entityManager){
        super(domainClass,entityManager);
        this.domainClass = domainClass;
        this.tableName = tableName;
    }

}
