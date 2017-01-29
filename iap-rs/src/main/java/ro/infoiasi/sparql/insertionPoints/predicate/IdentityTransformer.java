package ro.infoiasi.sparql.insertionPoints.predicate;


import ro.infoiasi.sparql.insertionPoints.function.PropertyFunction;

public enum IdentityTransformer implements PropertyFunction {
    STR("str");

    private final String fn;

    IdentityTransformer(String fn) {
        this.fn = fn;
    }

    @Override
    public String getFunction() {
        return fn;
    }


    @Override
    public String variableName(Class clazz, String variable) {
        return variable;
    }
}
