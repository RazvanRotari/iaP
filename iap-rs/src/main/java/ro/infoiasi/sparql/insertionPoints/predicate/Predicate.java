package ro.infoiasi.sparql.insertionPoints.predicate;


import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;

public abstract class Predicate {
    protected Class clazz;
    protected Transformer propertyTransformer;
    protected String variable;

    public Predicate(Class clazz, String variable, Transformer propertyTransformer) {
        this.clazz = clazz;
        this.propertyTransformer = propertyTransformer;
        this.variable = variable;
    }

    public abstract String construct(String value);

    public static abstract class Builder {
        protected Class clazz;
        protected String variable;
        protected Transformer propertyTransformer;

        public Builder setClazz(Class clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder setPropertyTransformer(Transformer propertyTransformer) {
            this.propertyTransformer = propertyTransformer;
            return this;
        }

        public Builder setVariable(String variable) {
            this.variable = variable;
            return this;
        }

        public abstract Predicate build();
    }
}
