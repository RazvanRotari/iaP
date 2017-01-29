package ro.infoiasi.sparql.insertionPoints.subqueries;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import ro.infoiasi.sparql.insertionPoints.filter.Filter;
import ro.infoiasi.sparql.insertionPoints.filter.NullFilter;
import ro.infoiasi.sparql.prefixes.annotations.Property;

import java.lang.reflect.Field;
import java.util.*;

public class AggregateSubQuery implements SubQuery{

    private final AggregatePropertyFunction function;
    private final String lead;
    private final String mapping;
    private List<String> variables;
    private Class clazz;

    public AggregateSubQuery(Class clazz, AggregatePropertyFunction function, String lead, String mapping, String... variables) {
        this.clazz = clazz;
        this.variables = new ArrayList<>(Arrays.asList(variables));
        this.variables.add(0, lead);
        this.function = function;
        this.mapping = mapping;
        this.lead = lead;
    }

    @Override
    public String construct() {
        return construct(new NullFilter());
    }

    private Triple<String,String,String> getLine(Property prop) {
        String subject = prop.variable();
        String property = prop.prefix().prefix + ":" + prop.field();
        String value = prop.variableName();
        return new ImmutableTriple<>(subject, property, value);
    }

    protected Set<String> getPrefixes() {
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

    @Override
    public String construct(Filter filter) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> prefixes = getPrefixes();
        for(String prefix: prefixes) {
            stringBuilder.append(prefix).append("\r\n");
        }
        stringBuilder.append("SELECT (").append(function.transform(clazz, lead)).append("as ?").append(mapping).append(")")
                .append("WHERE {").append("\r\n");
        for(String var: variables) {
            Property prop = function.getFieldMetaData(clazz, var);
            Triple triple = getLine(prop);
            stringBuilder.append("?").append(triple.getLeft()).append(" ").append(triple.getMiddle()).append(" ").append("?" + triple.getRight()).append(".\r\n");
        }
        stringBuilder.append(filter.construct()).append("\r\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
