package ro.infoiasi.sparql.insertionPoints.predicate;

import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;

public class Contains extends Predicate {

    public Contains(Class clazz, String variable) {
        super(clazz, variable, Transformer.REGEX);
    }

    @Override
    public String construct(String value) {
        return "regex(lcase(?" + variable + "), \"" + value + "\")";
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
        public Contains build() {
            return new Contains(clazz, variable);
        }
    }
}
