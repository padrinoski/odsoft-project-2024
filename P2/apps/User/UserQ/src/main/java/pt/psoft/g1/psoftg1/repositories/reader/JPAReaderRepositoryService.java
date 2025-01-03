package pt.psoft.g1.psoftg1.repositories.reader;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import pt.psoft.g1.psoftg1.dto.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.dto.SearchReadersQuery;
import pt.psoft.g1.psoftg1.interfaces.ReaderRepository;
import pt.psoft.g1.psoftg1.model.reader.ReaderDetails;
import pt.psoft.g1.psoftg1.model.user.Reader;
import pt.psoft.g1.psoftg1.shared.Constants;
import pt.psoft.g1.psoftg1.specifications.GenericSpecifications;

public class JPAReaderRepositoryService extends SimpleJpaRepository<ReaderDetails,String> implements ReaderRepository{

    private EntityManager entityManager;
    public JPAReaderRepositoryService(EntityManager entityManager) {
        super(ReaderDetails.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ReaderDetails> findByReaderNumber(@NotNull String readerNumber) {
        return super.findOne(GenericSpecifications.equals(readerNumber, Constants.READER_NUMBER));
    }

    @Override
    public List<ReaderDetails> findByPhoneNumber(@NotNull String phoneNumber) {
        return super.findAll(GenericSpecifications.equals(phoneNumber,Constants.PHONE_NUMBER));
    }

    @Override
    public Optional<ReaderDetails> findByUsername(@NotNull String username) {
        return super.findOne(GenericSpecifications.equals(username,Constants.READER,Constants.USERNAME_FIELD));
    }

    @Override
    public Optional<ReaderDetails> findByUserId(@NotNull String userId) {
        return super.findOne(GenericSpecifications.equals(userId,Constants.READER,Constants.USER_ID));
    }

    @Override
    public int getCountFromCurrentYear() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<ReaderDetails> readerDetailsRoot = cq.from(ReaderDetails.class);
        Join<ReaderDetails, Reader> readerJoin = readerDetailsRoot.join(Constants.READER);
        //Join<Reader, User> userJoin = readerJoin.join(Constants.USER);

        Expression<Integer> yearExpr = cb.function(Constants.YEAR, Integer.class, readerJoin.get(Constants.CREATED_AT));
        Expression<Integer> currentYearExpr = cb.function(Constants.YEAR, Integer.class, cb.currentDate());

        cq.select(cb.count(readerDetailsRoot))
                .where(cb.equal(yearExpr, currentYearExpr));

        return entityManager.createQuery(cq).getSingleResult().intValue();
    }
    // todo
//    @Override
//    public Page<ReaderDetails> findTopReaders(Pageable pageable) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<ReaderDetails> cq = cb.createQuery(ReaderDetails.class);
//        Root<ReaderDetails> readerDetailsRoot = cq.from(ReaderDetails.class);
//        Join<ReaderDetails, Lending> lendingJoin = readerDetailsRoot.join(Constants.LENDING);
//
//        Expression<Long> lendingCount = cb.count(lendingJoin.get(Constants.LENDING_ID));
//
//        cq.select(readerDetailsRoot)
//                .groupBy(readerDetailsRoot)
//                .orderBy(cb.desc(lendingCount));
//
//        TypedQuery<ReaderDetails> query = entityManager.createQuery(cq);
//        query.setFirstResult((int) pageable.getOffset());
//        query.setMaxResults(pageable.getPageSize());
//
//        List<ReaderDetails> results = query.getResultList();
//        return new PageImpl<>(results, pageable, results.size());
//    }

    // todo
//    @Override
//    public Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable, String genre, LocalDate startDate, LocalDate endDate) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<ReaderBookCountDTO> cq = cb.createQuery(ReaderBookCountDTO.class);
//        Root<ReaderDetails> readerDetailsRoot= cq.from(ReaderDetails.class);
//        Root<Lending> lendingRoot = cq.from(Lending.class);
//        Root<Book> bookRoot = cq.from(Book.class);
//        Root<Genre> genreRoot = cq.from(Genre.class);
//
//        Predicate lending_joinCondition = cb.equal(lendingRoot.get(Constants.READER_DETAILS).get(Constants.PK),  readerDetailsRoot.get(Constants.PK));
//        Predicate book_joinCondition = cb.equal(bookRoot.get(Constants.PK), lendingRoot.get(Constants.BOOK).get(Constants.PK));
//        Predicate genre_joinCondition = cb.equal(genreRoot.get("id"),bookRoot.get(Constants.GENRE_GENRE_NAME).get("id"));
//        cq.where(lending_joinCondition);
//        cq.where(book_joinCondition);
//        cq.where(genre_joinCondition);
//
//        Predicate genrePredicate = cb.equal(genreRoot.get(Constants.PK),genre);
//        Predicate datePredicate = cb.between(lendingRoot.get(Constants.START_DATE), startDate,endDate);
//
//        Expression<Long> lendingCount = cb.count(lendingRoot.get(Constants.LENDING_ID));
//        cq.where(cb.and(lending_joinCondition,book_joinCondition,genre_joinCondition,genrePredicate,datePredicate));
//        cq.select(cb.construct(ReaderBookCountDTO.class, readerDetailsRoot, lendingCount));
//        cq.groupBy(readerDetailsRoot.get(Constants.PK));
//        //cq.groupBy(lendingRoot.get(Constants.PK));
//        cq.orderBy(cb.desc(lendingCount));
//
//        TypedQuery<ReaderBookCountDTO> query = entityManager.createQuery(cq);
//        query.setFirstResult((int) pageable.getOffset());
//        query.setMaxResults(pageable.getPageSize());
//
//        List<ReaderBookCountDTO> results = query.getResultList();
//        return new PageImpl<>(results, pageable, results.size());
//    }

    @Override
    public List<ReaderDetails> searchReaderDetails(pt.psoft.g1.psoftg1.shared.services.Page page,
            SearchReadersQuery filters) {
        Specification<ReaderDetails> userNameSpec = GenericSpecifications.equals(filters.getEmail(), Constants.USERNAME_FIELD);
        Specification<ReaderDetails> nameSpec = GenericSpecifications.equals(filters.getName(), Constants.FULLNAME_FIELD);
        Specification<ReaderDetails> phoneNumberSpec = GenericSpecifications.equals(filters.getPhoneNumber(), Constants.PHONE_NUMBER);

        return super.findAll(userNameSpec.and(nameSpec).and(phoneNumberSpec), PageRequest.of(page.getNumber(), page.getLimit())).stream().toList();
    }

    
}
