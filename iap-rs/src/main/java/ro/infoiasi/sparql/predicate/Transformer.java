package ro.infoiasi.sparql.predicate;


public enum Transformer {
    STR("str");

    private final String fn;

    Transformer(String fn) {
        this.fn = fn;
    }

    public String transform(String variable) {
        return fn + "(?" + variable + ")";
    }
}
