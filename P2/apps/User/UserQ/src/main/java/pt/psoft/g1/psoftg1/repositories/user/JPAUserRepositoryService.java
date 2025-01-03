package pt.psoft.g1.psoftg1.repositories.user;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.dto.SearchUsersQuery;
import pt.psoft.g1.psoftg1.interfaces.UserRepository;
import pt.psoft.g1.psoftg1.model.user.User;
import pt.psoft.g1.psoftg1.shared.Constants;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.specifications.GenericSpecifications;


@Repository
public class JPAUserRepositoryService extends SimpleJpaRepository<User, String> implements UserRepository {

    private EntityManager entityManager;
    public JPAUserRepositoryService(EntityManager entityManager) {
        super(User.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findById(String objectId) {
        return super.findById(objectId);
    }

    @Override
    public User getById(String id) {
        return super.getReferenceById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {return super.findOne(GenericSpecifications.equals(username, Constants.USERNAME_FIELD));}

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        Specification<User> fullNameSpec = GenericSpecifications.equals(query.getFullName(), Constants.FULLNAME_FIELD);
        Specification<User> userNameSpec = GenericSpecifications.equals(query.getUsername(), Constants.USERNAME_FIELD);

        return super.findAll(fullNameSpec.and(userNameSpec), Pageable.ofSize(page.getLimit())).stream().toList();
    }

    @Override
    public List<User> findByName(String name) {
        return super.findAll(GenericSpecifications.equals(name, Constants.FULLNAME_FIELD));
    }

    @Override
    public List<User> findByNameContains(String name) {
        return super.findAll(GenericSpecifications.contains(name, Constants.FULLNAME_FIELD, Constants.FULLNAME_FIELD));
    }

}
