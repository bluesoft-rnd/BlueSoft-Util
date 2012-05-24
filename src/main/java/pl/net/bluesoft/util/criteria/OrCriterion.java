package pl.net.bluesoft.util.criteria;

public class OrCriterion extends JunctionCriterion {

    public OrCriterion(Criterion... criteria) {
        super(OP_OR, criteria);
    }
}
