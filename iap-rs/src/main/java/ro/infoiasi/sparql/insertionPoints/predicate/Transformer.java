package ro.infoiasi.sparql.insertionPoints.predicate;


import ro.infoiasi.sparql.insertionPoints.Function;

public enum Transformer implements Function{
    STR("str");

    private final String fn;

    Transformer(String fn) {
        this.fn = fn;
    }

    @Override
    public String getFunction() {
        return fn;
    }
}
