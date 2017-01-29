package ro.infoiasi.sparql.insertionPoints.predicate;

import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;

public class LessThan extends Predicate {

    public LessThan(Class clazz, String variable, Transformer propertyTransformer) {
        super(clazz, variable, propertyTransformer);
    }

    @Override
    public String construct(String value) {
        return propertyTransformer.transform(clazz, variable) + " < " + quote(value);
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

    public static class Builder extends Predicate.Builder {

        @Override
        public LessThan build() {
            return new LessThan(clazz, variable, propertyTransformer);
        }
    }
}
