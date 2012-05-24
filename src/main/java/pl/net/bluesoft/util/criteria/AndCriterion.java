package pl.net.bluesoft.util.criteria;

public class AndCriterion extends JunctionCriterion {

    public AndCriterion(Criterion... criteria) {
        super(OP_AND, criteria);
    }
}
