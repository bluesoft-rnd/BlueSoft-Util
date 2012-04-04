package pl.net.bluesoft.util.criteria;

import static pl.net.bluesoft.util.criteria.lang.Formats.join;

/**
 * @author: amichalak@bluesoft.net.pl
 */
public class Order extends PropertyBasedCriterion {
    public Order(String propertyName) {
        super(propertyName, ORDER_ASC);
    }

    public Order(String propertyName, boolean desc) {
        super(propertyName, desc ? ORDER_DESC : ORDER_ASC);
    }

    @Override
    public String toSql(QueryMetadata metadata) {
        return join(" ", propertyName, value);
    }
}
