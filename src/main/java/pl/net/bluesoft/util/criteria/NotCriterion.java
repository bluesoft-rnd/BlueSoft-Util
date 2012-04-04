package pl.net.bluesoft.util.criteria;

import pl.net.bluesoft.util.criteria.lang.Formats;

public class NotCriterion implements Criterion {
    protected Criterion criterion;

    public NotCriterion(Criterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public String toSql(QueryMetadata metadata) {
        return Formats.join(OP_NOT + " ", criterion.toSql(metadata));
    }
}
