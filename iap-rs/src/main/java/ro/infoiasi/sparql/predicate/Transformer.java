package ro.infoiasi.sparql.predicate;


import ro.infoiasi.sparql.prefixes.Property;

import java.lang.reflect.Field;

public enum Transformer {
    STR("str");

    private final String fn;

    Transformer(String fn) {
        this.fn = fn;
    }

    public String transform(Class clazz, String name) {
        return fn + "(?" + variable(clazz, name) + ")";
    }

    private String variable(Class clazz, String name) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                if(property.variable().equals(name)) {
                    return property.variableName();
                }
            }
        }
        throw new IllegalArgumentException("Attribute " + name + " not found for: " + clazz);
    }
}
