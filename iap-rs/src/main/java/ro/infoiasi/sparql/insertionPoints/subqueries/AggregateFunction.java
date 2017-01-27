package ro.infoiasi.sparql.insertionPoints.subqueries;

import ro.infoiasi.sparql.insertionPoints.Function;

public enum AggregateFunction implements Function {
    COUNT("COUNT"), SUM("SUM"), AVG("AVG"), MIN("MIN"), MAX("MAX"), CONCAT("CONCAT");

    private String fn;

    AggregateFunction(String fn) {
        this.fn = fn;
    }

    @Override
    public String getFunction() {
        return fn;
    }
}
