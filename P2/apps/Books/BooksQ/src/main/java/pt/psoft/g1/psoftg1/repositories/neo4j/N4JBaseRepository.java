package pt.psoft.g1.psoftg1.repositories.neo4j;

import lombok.AllArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.interfaces.GetID;

import java.util.Optional;
import java.util.stream.StreamSupport;

@AllArgsConstructor

public class N4JBaseRepository <T extends GetID<ID>,ID> implements CrudRepository<T,ID> {
    protected final Neo4jTemplate neo4jTemplate;
    private final Class<T> classT;

    @Override
    public <S extends T> S save(S entity) {
        return neo4jTemplate.save(entity);
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        return neo4jTemplate.saveAll(entities);
    }

    @Override
    public Optional<T> findById(ID id) {
        return neo4jTemplate.findById(id, classT);
    }

    @Override
    public boolean existsById(ID aLong) {
        return neo4jTemplate.existsById(aLong, classT);
    }

    @Override
    public Iterable<T> findAll() {
        return neo4jTemplate.findAll(classT);
    }

    @Override
    public Iterable<T> findAllById(Iterable<ID> ids) {
        return neo4jTemplate.findAllById(ids, classT);
    }

    @Override
    public long count() {
        return neo4jTemplate.count(classT);
    }

    @Override
    public void deleteById(ID id) {
        neo4jTemplate.deleteById(id, classT);
    }

    @Override
    public void delete(T entity) {
        neo4jTemplate.deleteById(entity.getID(), classT);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        neo4jTemplate.deleteAllById(ids, classT);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        neo4jTemplate.deleteAllById(StreamSupport.stream(entities.spliterator(), false).map(t -> t.getID()).toList(), classT);
    }

    @Override
    public void deleteAll() {
        neo4jTemplate.deleteAll(classT);
    }
}
