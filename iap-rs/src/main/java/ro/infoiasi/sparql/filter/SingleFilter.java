package ro.infoiasi.sparql.filter;

import ro.infoiasi.sparql.predicate.Predicate;

public class SingleFilter implements Filter{

    private Predicate predicate;
    private String value;

    public SingleFilter(Predicate predicate, String value) {
        this.predicate = predicate;
        this.value = value;
    }

    public String construct() {
        return "FILTER(" + predicate.construct(value) + ")";
    }

    @Override
    public String toString() {
        return "SingleFilter{" +
                "predicate=" + predicate +
                ", value='" + value + '\'' +
                '}';
    }
}
