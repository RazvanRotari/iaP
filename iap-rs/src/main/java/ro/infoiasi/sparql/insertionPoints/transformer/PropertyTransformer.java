package ro.infoiasi.sparql.insertionPoints.transformer;


import ro.infoiasi.sparql.insertionPoints.function.PropertyFunction;

public enum PropertyTransformer implements PropertyFunction {
    STR("str"), NONE("");

    private final String fn;

    PropertyTransformer(String fn) {
        this.fn = fn;
    }

    @Override
    public String getFunction() {
        return fn;
    }
}
