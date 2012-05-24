package pl.net.bluesoft.util.criteria;

public class LikeCriterion extends OperatorCriterion {

    public LikeCriterion(String propertyName, Object value) {
        super(propertyName, value, OP_LIKE);
    }
}
