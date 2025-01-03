package pt.psoft.g1.psoftg1.specifications;

import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class GenericSpecifications {

    public static <T> Specification<T> equals(String value, String property){
        return (root,query,builder) -> builder.equal(root.get(property).as(String.class),value);
    }

    public static <T> Specification<T> equals(LocalDate value, String property){
        return (root,query,builder) -> builder.equal(root.get(property),value);
    }

    public static <T> Specification<T> isIn(String value, String property, String secondProperty){
        return (root,query,builder) -> builder.isMember(value, root.join(property).get(secondProperty));
    }

    public static <T> Specification<T> betweenDates(LocalDate startDate, LocalDate endDate, String property, String secondProperty) {
        return (root, query, builder) -> builder.between(root.get(property).get(secondProperty), startDate, endDate);
    }

    public static <T> Specification<T> betweenDates(LocalDate startDate, LocalDate endDate, String property) {
        return (root, query, builder) -> builder.between(root.get(property), startDate, endDate);
    }
    public static <T> Specification<T> contains(String value, String property){

        return (root,query,builder) -> {

            Expression<String> stringParam = root.get(property).as(String.class);
            return builder.like(stringParam, "%" + value + "%" );
        };
    }

    public static <T> Specification<T> contains(String value, String property, String secondProperty){

        return (root,query,builder) -> {

            Expression<String> stringParam = root.get(property).get(secondProperty).as(String.class);
            return builder.like(stringParam, "%" + value + "%" );
        };
    }
    
    public static <T> Specification<T> equals(String value, String property, String secondProperty, String thirdProperty){
        return (root,query,builder) -> builder.equal(root.get(property).get(secondProperty).get(thirdProperty).as(String.class),value);
    }

    public static <T> Specification<T> equals(String value, String property, String secondProperty){
        return (root,query,builder) -> builder.equal(root.get(property).get(secondProperty).as(String.class),value);
    }

    public static <T> Specification<T> returned(Boolean returned, String property) {
        return (root, query, builder) -> {
            if (returned) {
                // Check if property returnedDate has a value (not null)
                return builder.isNotNull(root.get(property));
            } else {
                // Check if property returnedDate is null
                return builder.isNull(root.get(property));
            }
        };
    }
}
