package pl.net.bluesoft.util.criteria;

import pl.net.bluesoft.util.criteria.lang.Formats;
import pl.net.bluesoft.util.lang.Collections;
import pl.net.bluesoft.util.lang.Transformer;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class JunctionCriterion implements Criterion {
    protected List<Criterion> criteria;
    protected String op;

    public JunctionCriterion(String op, Criterion... criteria) {
        this(op, Arrays.asList(criteria));
    }

    public JunctionCriterion(String op, Collection<Criterion> criteria) {
        if (criteria == null || criteria.size() == 0) {
            throw new IllegalArgumentException("Junction criterion should be given more than 0 criterion");
        }
        this.op = op;
        this.criteria = new LinkedList<Criterion>(criteria);
    }

    @Override
    public String toSql(final QueryMetadata metadata) {
        if (criteria.size() > 1) {
            Collection<String> sql = Collections.collect(criteria, new Transformer<Criterion, String>() {
                @Override
                public String transform(Criterion obj) {
                    return obj.toSql(metadata);
                }
            });
            return "(" + Formats.join(" " + op + " ", sql) + ")";
        }
        else {
            Criterion c = criteria.get(0);
            return c.toSql(metadata);
        }
    }

    public String getOperator() {
        return op;
    }

    public List<Criterion> getCriteria() {
        return criteria;
    }
}
