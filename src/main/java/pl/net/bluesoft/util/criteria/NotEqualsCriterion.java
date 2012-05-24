package pl.net.bluesoft.util.criteria;

import static pl.net.bluesoft.util.criteria.lang.Formats.join;

public class NotEqualsCriterion extends OperatorCriterion {

    public NotEqualsCriterion(String propertyName, Object value) {
        super(propertyName, value, OP_NE);
    }

    @Override
    public String toSql(QueryMetadata metadata) {
        if (value == null) {
            return join(" ", metadata.getColumnName(propertyName), IS_NOT_NULL);
        }
        return super.toSql(metadata);
    }
}
