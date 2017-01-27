package ro.infoiasi.sparql.predicate;

public class Equals implements Predicate {

    private Class clazz;
    private Transformer transformer;
    private String variable;

    public Equals(Class clazz, String variable, Transformer transformer) {
        this.clazz = clazz;
        this.transformer = transformer;
        this.variable = variable;
    }

    @Override
    public String construct(String value) {
        return transformer.transform(clazz, variable) + " = " + quote(value);
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
