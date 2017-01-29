package ro.infoiasi.sparql.insertionPoints.subqueries;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.jena.shared.NotFoundException;
import ro.infoiasi.sparql.insertionPoints.QueryInsertionPoint;
import ro.infoiasi.sparql.insertionPoints.filter.Filter;
import ro.infoiasi.sparql.prefixes.annotations.Property;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.TreeSet;

public abstract class SubQuery implements QueryInsertionPoint {
    protected boolean includePrefixes = true;
    private Class clazz;

    public SubQuery(Class clazz) {
        this.clazz = clazz;
    }

    public abstract String construct(Filter filter);

    public void excludePrefixes() {
        includePrefixes = false;
    }

    protected Property getFieldMetaData(String variable) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                if(property.variableName().equals(variable)) {
                    return property;
                }
            }
        }
        throw new NotFoundException("Cannot find " + variable + " in " + clazz);
    }

    public Set<String> getPrefixes() {
        Set<String> dependencies = new TreeSet<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                String prefix = property.prefix().prefix;
                String url = property.prefix().url;
                String dependency = "PREFIX " + prefix + ": <" + url + ">";
                dependencies.add(dependency);
            }
        }
        return dependencies;
    }

    protected Triple<String,String,String> getLine(Property prop) {
        String subject = "resourceId";
        String property = prop.prefix().prefix + ":" + prop.field();
        String value = prop.variableName();
        return new ImmutableTriple<>(subject, property, value);
    }

    protected Class getClazz() {
        return clazz;
    }
}
