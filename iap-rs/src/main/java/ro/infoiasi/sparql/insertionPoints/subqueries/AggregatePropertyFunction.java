package ro.infoiasi.sparql.insertionPoints.subqueries;

import ro.infoiasi.sparql.insertionPoints.function.Function;

public enum AggregatePropertyFunction implements Function {
    COUNT("COUNT"), SUM("SUM"), AVG("AVG"), MIN("MIN"), MAX("MAX"), CONCAT("CONCAT");

    private String fn;

    AggregatePropertyFunction(String fn) {
        this.fn = fn;
    }

    public String transform(Class clazz, String variable) {
        return getFunction() + "(?" + variable + ")";
    }

    @Override
    public String getFunction() {
        return fn;
    }
}
