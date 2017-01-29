package ro.infoiasi.sparql.insertionPoints.subqueries;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.jena.shared.NotFoundException;
import ro.infoiasi.sparql.insertionPoints.filter.Filter;
import ro.infoiasi.sparql.insertionPoints.filter.NullFilter;
import ro.infoiasi.sparql.prefixes.annotations.Property;

import java.lang.reflect.Field;
import java.util.*;

public class AggregateSubQuery extends SubQuery {

    private final AggregatePropertyFunction function;
    private final String lead;
    private final String mapping;
    private List<String> variables;

    public AggregateSubQuery(Class clazz, AggregatePropertyFunction function, String lead, String mapping, String... variables) {
        super(clazz);
        this.variables = new ArrayList<>(Arrays.asList(variables));
        this.variables.add(0, lead);
        this.function = function;
        this.mapping = mapping;
        this.lead = lead;
    }

    @Override
    public String construct() throws Exception {
        return construct(new NullFilter());
    }

    @Override
    public String construct(Filter filter) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        if(includePrefixes) {
            Set<String> prefixes = getPrefixes();
            for (String prefix : prefixes) {
                stringBuilder.append(prefix).append("\r\n");
            }
        }
        stringBuilder.append("SELECT (").append(function.transform(getClazz(), lead)).append("as ?").append(mapping).append(")")
                .append("WHERE {").append("\r\n");
        for(String var: variables) {
            Property prop = getFieldMetaData(var);
            Triple triple = getLine(prop);
            stringBuilder.append("?").append(triple.getLeft()).append(" ").append(triple.getMiddle()).append(" ").append("?" + triple.getRight()).append(".\r\n");
        }
        stringBuilder.append(filter.construct()).append("\r\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
