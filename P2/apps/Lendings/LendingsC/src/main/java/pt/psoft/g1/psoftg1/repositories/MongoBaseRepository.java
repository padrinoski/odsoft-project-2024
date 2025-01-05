package pt.psoft.g1.psoftg1.repositories;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.interfaces.GetID;

import java.util.Objects;
import java.util.stream.StreamSupport;
import java.util.Optional;

@AllArgsConstructor
public class MongoBaseRepository<T extends GetID<ID>, ID> implements CrudRepository<T, ID> {

   protected final MongoTemplate mongoTemplate;

   private final Class<T> domainClass;

   @Override
   public <S extends T> S save(S entity) {
      return mongoTemplate.save(entity);
   }

   @Override
   public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
      return StreamSupport.stream(entities.spliterator(), false).map(mongoTemplate::save).toList();
   }

   @Override
   public Optional<T> findById(ID id) {
      return Optional.ofNullable(mongoTemplate.findById(id, domainClass));
   }

   @Override
   public boolean existsById(ID id) {
      return mongoTemplate.findById(id, domainClass) != null;
   }

   @Override
   public Iterable<T> findAll() {
      return mongoTemplate.findAll(domainClass);
   }

   @Override
   public Iterable<T> findAllById(Iterable<ID> ids) {
      return StreamSupport.stream(ids.spliterator(), false).map(id -> mongoTemplate.findById(id, domainClass)).filter(Objects::nonNull).toList();
   }

   @Override
   public long count() {
      Query query = new Query();
      return mongoTemplate.findAll(domainClass).size();
   }

   @Override
   public void deleteById(ID id) {
      Optional<T> object = findById(id);
      object.ifPresent(this::delete);
   }

   @Override
   public void delete(T entity) {
      mongoTemplate.remove(entity);
   }

   @Override
   public void deleteAllById(Iterable<? extends ID> ids) {
      for (ID id : ids) {
         deleteById(id);
      }
   }

   @Override
   public void deleteAll(Iterable<? extends T> entities) {
      for (T entity : entities) {
         delete(entity);
      }
   }

   @Override
   public void deleteAll() {
      for (T entity : mongoTemplate.findAll(domainClass)) {
         mongoTemplate.remove(entity);
      }
   }
}