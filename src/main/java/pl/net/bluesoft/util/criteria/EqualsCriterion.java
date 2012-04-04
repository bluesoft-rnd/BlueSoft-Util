package pl.net.bluesoft.util.criteria;

import static pl.net.bluesoft.util.criteria.lang.Formats.join;

public class EqualsCriterion extends OperatorCriterion {

    public EqualsCriterion(String propertyName, Object value) {
        super(propertyName, value, OP_EQ);
    }

    @Override
    public String toSql(QueryMetadata metadata) {
        if (value == null) {
            return join(" ", metadata.getColumnName(propertyName), IS_NULL);
        }
        return super.toSql(metadata);
    }
}
