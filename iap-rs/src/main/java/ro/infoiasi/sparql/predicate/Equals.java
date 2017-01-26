package ro.infoiasi.sparql.predicate;

public class Equals implements Predicate {

    private Transformer transformer;
    private String variable;

    public Equals(String variable, Transformer transformer) {
        this.transformer = transformer;
        this.variable = variable;
    }

    @Override
    public String construct(String value) {
        return transformer.transform(variable) + " = " + quote(value);
    }

    private String quote(String value) {
        return "\"" + value + "\"";
    }

    @Override
    public String toString() {
        return "Equals{" +
                "transformer=" + transformer +
                ", variable='" + variable + '\'' +
                '}';
    }
}
