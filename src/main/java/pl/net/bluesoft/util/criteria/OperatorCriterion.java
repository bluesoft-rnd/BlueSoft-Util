package pl.net.bluesoft.util.criteria;

import static pl.net.bluesoft.util.criteria.lang.Formats.join;

public class OperatorCriterion extends PropertyBasedCriterion {
    protected String op;

    public OperatorCriterion(String propertyName, Object value, String op) {
        super(propertyName, value);
        this.op = op;
    }

    @Override
    public String toSql(QueryMetadata metadata) {
        return join(" ", metadata.getColumnName(propertyName), op, metadata.formatValue(value));
    }

    public String getOperator() {
        return op;
    }
}
