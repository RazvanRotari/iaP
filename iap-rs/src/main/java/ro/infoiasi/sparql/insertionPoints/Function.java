package ro.infoiasi.sparql.insertionPoints;

import ro.infoiasi.sparql.prefixes.Property;

import java.lang.reflect.Field;

public interface Function {

    String getFunction();

    default String transform(Class clazz, String variable) {
        return getFunction() + "(?" + variableName(clazz, variable) + ")";
    }

    default String variableName(Class clazz, String variable) {
        return getFieldMetaData(clazz, variable).variableName();
    }

    default Property getFieldMetaData(Class clazz, String name) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                if(property.variable().equals(name)) {
                    return property;
                }
            }
        }
        throw new IllegalArgumentException("Attribute " + name + " not found for: " + clazz);
    }
}