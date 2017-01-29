package ro.infoiasi.sparql.insertionPoints.predicate;

public class Equals implements Predicate {

    private Class clazz;
    private Transformer propertyTransformer;
    private String variable;

    public Equals(Class clazz, String variable, Transformer propertyTransformer) {
        this.clazz = clazz;
        this.propertyTransformer = propertyTransformer;
        this.variable = variable;
    }

    @Override
    public String construct(String value) {
        return propertyTransformer.transform(clazz, variable) + " = " + quote(value);
    }

    private String quote(String value) {
        return "\"" + value + "\"";
    }

    @Override
    public String toString() {
        return "Equals{" +
                "propertyTransformer=" + propertyTransformer +
                ", variableName='" + variable + '\'' +
                '}';
    }
}
