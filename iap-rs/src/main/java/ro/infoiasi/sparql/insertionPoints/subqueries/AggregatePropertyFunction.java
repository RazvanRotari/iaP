package ro.infoiasi.sparql.insertionPoints.subqueries;

import ro.infoiasi.sparql.insertionPoints.function.PropertyFunction;

public enum AggregatePropertyFunction implements PropertyFunction {
    COUNT("COUNT"), SUM("SUM"), AVG("AVG"), MIN("MIN"), MAX("MAX"), CONCAT("CONCAT");

    private String fn;

    AggregatePropertyFunction(String fn) {
        this.fn = fn;
    }

    @Override
    public String getFunction() {
        return fn;
    }
}
