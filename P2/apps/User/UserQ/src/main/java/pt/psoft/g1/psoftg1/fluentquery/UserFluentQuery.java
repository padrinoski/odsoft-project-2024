package pt.psoft.g1.psoftg1.fluentquery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import pt.psoft.g1.psoftg1.model.user.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class UserFluentQuery implements FluentQuery.FetchableFluentQuery<User>{


    @Override
    public FetchableFluentQuery<User> sortBy(Sort sort) {
        return (FetchableFluentQuery<User>) Sort.unsorted();
    }

    @Override
    public <R> FetchableFluentQuery<R> as(Class<R> resultType) {
        return null;
    }

    @Override
    public FetchableFluentQuery<User> project(Collection<String> properties) {
        return null;
    }

    @Override
    public User oneValue() {
        return null;
    }

    @Override
    public User firstValue() {
        return null;
    }

    @Override
    public List<User> all() {
        return null;
    }

    @Override
    public Page<User> page(Pageable pageable) {
        return null;
    }

    @Override
    public Stream<User> stream() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean exists() {
        return false;
    }
}
