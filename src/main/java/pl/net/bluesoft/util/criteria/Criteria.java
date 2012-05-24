package pl.net.bluesoft.util.criteria;

import java.util.Set;

public abstract class Criteria {
    public static Criterion in(String fieldName, Set values) {
        return new InCriterion(fieldName, values);
    }

    public static Criterion in(String fieldName, Object... values) {
        return new InCriterion(fieldName, values);
    }

    public static Criterion eq(String fieldName, Object value) {
        return new EqualsCriterion(fieldName, value);
    }

    public static Criterion ne(String fieldName, Object value) {
        return new NotEqualsCriterion(fieldName, value);
    }

    public static Criterion and(Criterion... criteria) {
        return new AndCriterion(criteria);
    }

    public static Criterion or(Criterion... criteria) {
        return new OrCriterion(criteria);
    }

    public static Criterion like(String fieldName, Object value) {
        return new LikeCriterion(fieldName, value);
    }

    public static Criterion notNull(String fieldName) {
        return new NotEqualsCriterion(fieldName, null);
    }

    public static Criterion isNull(String fieldName) {
        return new EqualsCriterion(fieldName, null);
    }

    public static Criterion not(Criterion criterion) {
        return new NotCriterion(criterion);
    }

    public static Criterion order(String fieldName, boolean desc) {
        return new Order(fieldName, desc);
    }

    public static Criterion order(String fieldName) {
        return new Order(fieldName);
    }
}
