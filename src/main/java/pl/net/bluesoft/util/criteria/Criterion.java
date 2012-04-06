package pl.net.bluesoft.util.criteria;

import pl.net.bluesoft.util.criteria.lang.Keywords;

public interface Criterion extends Keywords {
    String toSql(QueryMetadata metadata);
}
