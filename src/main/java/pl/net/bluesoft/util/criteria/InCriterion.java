package pl.net.bluesoft.util.criteria;

import java.util.Set;

import static pl.net.bluesoft.util.criteria.lang.Formats.join;

public class InCriterion extends PropertyBasedCriterion {
    protected Object[] values;

    public InCriterion(String propertyName, Set values) {
        super(propertyName);
        this.values = values.toArray();
    }

    public InCriterion(String propertyName, Object... values) {
        super(propertyName);
        this.values = values;
    }

    @Override
    public String toSql(QueryMetadata metadata) {
        if (values.length > 0) {
            for (int i = 0; i < values.length; ++i) {
                values[i] = metadata.formatValue(values[i]);
            }
            return join(" ", metadata.getColumnName(propertyName), OP_IN, "(", join(", ", values), ")");
        }
        return "";
    }
}
