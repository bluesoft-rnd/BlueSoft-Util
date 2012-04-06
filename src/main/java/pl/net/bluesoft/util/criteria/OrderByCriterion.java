package pl.net.bluesoft.util.criteria;

import pl.net.bluesoft.util.lang.Collections;
import pl.net.bluesoft.util.lang.Strings;
import pl.net.bluesoft.util.lang.Transformer;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static pl.net.bluesoft.util.criteria.lang.Formats.join;

/**
 * @author: amichalak@bluesoft.net.pl
 */
public class OrderByCriterion implements Criterion {
    protected List<Order> orders;

    public OrderByCriterion(Order... groupBies) {
        this(Arrays.asList(groupBies));
    }

    public OrderByCriterion(Collection<Order> groupBies) {
        this.orders = new LinkedList<Order>(groupBies);
    }

    @Override
    public String toSql(final QueryMetadata metadata) {
        Collection<String> sql = Collections.collect(orders, new Transformer<Order, String>() {
            @Override
            public String transform(Order obj) {
                return obj.toSql(metadata);
            }
        });
        String result = join(", ", sql);
        return Strings.hasText(result) ? ORDER_BY + " " + result : "";
    }
}
