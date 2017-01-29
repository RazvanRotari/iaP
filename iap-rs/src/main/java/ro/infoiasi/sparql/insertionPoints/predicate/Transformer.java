package ro.infoiasi.sparql.insertionPoints.predicate;


import ro.infoiasi.sparql.insertionPoints.function.Function;

public interface Transformer extends Function {

    String transform(Class clazz, String variable);

}
