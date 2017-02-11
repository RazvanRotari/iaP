package ro.infoiasi.sparql.insertionPoints.transformer;


import ro.infoiasi.sparql.insertionPoints.function.Function;

public enum Transformer implements Function {
    STR("str"), NONE(""), REGEX("regex");

    private final String fn;

    Transformer(String str) {
        this.fn = str;
    }

    public String transform(Class clazz, String variable) {
        return getFunction() + "(?" + variable + ")";
    }

    @Override
    public String getFunction() {
        return fn;
    }
}
