package ro.infoiasi.sparql.insertionPoints.transformer;

import ro.infoiasi.sparql.insertionPoints.function.ReferenceFunction;

public enum ReferenceTransformer implements ReferenceFunction {
    STR("str"), NONE("");

    private final String fn;

    ReferenceTransformer(String fn) {
        this.fn = fn;
    }

    @Override
    public String getFunction() {
        return fn;
    }
}
