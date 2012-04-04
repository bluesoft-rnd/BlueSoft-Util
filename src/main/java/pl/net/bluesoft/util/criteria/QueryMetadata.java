package pl.net.bluesoft.util.criteria;

import java.util.HashMap;
import java.util.Map;

public abstract class QueryMetadata {
    protected Map<Object, String> properties;

    public void setQueryProperty(Object key, String value) {
        if (properties == null) {
            properties = new HashMap<Object, String>();
        }
        properties.put(key, value);
    }

    public String getQueryProperty(Object key) {
        return properties != null ? properties.get(key) : null;
    }

    public abstract String getColumnName(String fieldName);
    public abstract String formatValue(Object value);
}
