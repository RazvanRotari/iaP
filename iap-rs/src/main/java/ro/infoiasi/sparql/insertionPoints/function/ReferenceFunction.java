package ro.infoiasi.sparql.insertionPoints.function;

import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;
import ro.infoiasi.sparql.prefixes.annotations.OneToOne;

import java.lang.reflect.Field;

public interface ReferenceFunction extends Transformer {

    default String transform(Class clazz, String variable) {
        return getFunction() + "(?" + variableName(clazz, variable) + ")";
    }

    default String variableName(Class clazz, String variable) {
        return getFieldMetaData(clazz, variable).variableName();
    }

    default OneToOne getFieldMetaData(Class clazz, String name) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(OneToOne.class)) {
                OneToOne property = field.getAnnotation(OneToOne.class);
                if(property.variable().equals(name)) {
                    return property;
                }
            }
        }
        throw new IllegalArgumentException("Attribute " + name + " not found for: " + clazz);
    }
}
