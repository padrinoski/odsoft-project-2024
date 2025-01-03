package pt.psoft.g1.psoftg1.shared.infraestructure.repositories.impl;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import pt.psoft.g1.psoftg1.shared.Constants;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;
import pt.psoft.g1.psoftg1.shared.repositories.ForbiddenNameRepository;
import pt.psoft.g1.psoftg1.specifications.GenericSpecifications;

import java.util.List;
import java.util.Optional;

public class JPAForbiddenNameRepositoryService extends SimpleJpaRepository<ForbiddenName, String> implements ForbiddenNameRepository{
    public JPAForbiddenNameRepositoryService(EntityManager entityManager) {
        super(ForbiddenName.class, entityManager);
    } 
    @Override 
    public ForbiddenName save(ForbiddenName forbiddenName) {
        return super.save(forbiddenName);
    }
    @Override
    public List<ForbiddenName> findByForbiddenNameIsContained(String pat) {
        return super.findAll(GenericSpecifications.contains(pat,Constants.FORBIDDEN_NAME_NAME));
    }

    @Override
    public Optional<ForbiddenName> findByForbiddenName(String forbiddenName) {
        return super.findOne(GenericSpecifications.equals(forbiddenName,Constants.FORBIDDEN_NAME_NAME));
    }

    @Override
    public int deleteForbiddenName(String forbiddenName) {
        return (int) super.delete(GenericSpecifications.equals(forbiddenName,Constants.FORBIDDEN_NAME_NAME));
    }

    @Override
    public void deleteInBatch(Iterable<ForbiddenName> entities) {
        super.deleteAllInBatch(entities);
    }
}
