package pl.net.bluesoft.util.criteria;

public abstract class PropertyBasedCriterion implements Criterion {
    protected String propertyName;
    protected Object value;

    protected PropertyBasedCriterion() {
    }

    protected PropertyBasedCriterion(String propertyName) {
        this.propertyName = propertyName;
    }

    public PropertyBasedCriterion(String propertyName, Object value) {
        this.propertyName = propertyName;
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Object getValue() {
        return value;
    }
}
