package ro.infoiasi.sparql.insertionPoints.predicate;

import ro.infoiasi.sparql.insertionPoints.function.ReferenceFunction;

public enum ReferenceTransformer implements ReferenceFunction {
    STR("str");

    private final String fn;

    ReferenceTransformer(String fn) {
        this.fn = fn;
    }

    @Override
    public String getFunction() {
        return fn;
    }
}
