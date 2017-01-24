package ro.infoiasi.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.shared.NotFoundException;
import org.apache.jena.sparql.modify.UpdateProcessRemote;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import ro.infoiasi.dao.entity.Entity;
import ro.infoiasi.sparql.prefixes.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericDAO<T extends Entity> {

    private static final boolean DEBUG = true;
    private final Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static final String HTTP_ENDPOINT = "http://razvanrotari.me:3030/default";

    protected Map<String, String> getDependencies() {
        Map<String, String> map = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                String prefix = property.prefix().prefix;
                String url = property.prefix().url;
                map.put(prefix, url);
            }
        }
        return map;
    }

    /**
     * TODO: cleanup
     * @param entity
     * @return
     * @throws Exception
     */
    protected List<Triple<String, String, String>> getTriples(T entity) throws Exception {
        List<Triple<String, String, String>> result = new ArrayList<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Property.class)) {
                Property prop = field.getAnnotation(Property.class);
                String subject = entity.getUniqueIdentifier();
                String property = prop.prefix().prefix + ":" + prop.field();
                String value;
                Method method = entity.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
                Object o = method.invoke(entity);
                value = String.valueOf(o);
                Triple<String, String, String> triple =
                        new ImmutableTriple<>(subject, property, "\"" + value + "\"");
                result.add(triple);
            }
        }
        return result;
    }

    protected List<Triple<String, String, String>> getFindTriples() throws Exception {
        List<Triple<String, String, String>> result = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Property.class)) {
                Property prop = field.getAnnotation(Property.class);
                String subject = prop.variable();
                String property = prop.prefix().prefix + ":" + prop.field();
                String value = prop.variable()+"Value";
                Triple<String, String, String> triple =
                        new ImmutableTriple<>(subject, property, value);
                result.add(triple);
            }
        }
        return result;
    }

    public void create(T entity) throws Exception {
        String queryString = buildInsertQuery(entity);
        if(DEBUG) {
            System.out.println(queryString);
        }
        UpdateRequest request = UpdateFactory.create(queryString);
        UpdateProcessRemote remote = new UpdateProcessRemote(request, HTTP_ENDPOINT, null);
        remote.execute();
    }

    public void update(T entity) throws Exception {
        String queryString = buildUpdateQuery(entity);
        if(DEBUG) {
            System.out.println(queryString);
        }
        UpdateRequest request = UpdateFactory.create(queryString);
        UpdateProcessRemote remote = new UpdateProcessRemote(request, HTTP_ENDPOINT, null);
        remote.execute();
    }

    public T find(String variable, String value) throws Exception {
        String findQuery = buildFindQuery(variable, value);
        if(DEBUG) {
            System.out.println(findQuery);
        }
        ResultSet resultSet = QueryExecutionFactory.sparqlService(HTTP_ENDPOINT, findQuery).execSelect();
        if(resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            return toEntity(solution);
        }
        throw new NotFoundException("Cannot find entity of type" + clazz + " with field: " + variable );
    }

    protected abstract T toEntity(QuerySolution solution);

    protected String buildInsertQuery(T entity) throws Exception {
        Map<String, String> dependencies = getDependencies();
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : dependencies.keySet()) {
            stringBuilder.append("PREFIX ").append(key).append(":")
                    .append(" <").append(dependencies.get(key)).append(">");
        }
        stringBuilder.append("\r\n")
                .append("INSERT DATA {").append("\r\n");
        List<Triple<String, String, String>> fields = getTriples(entity);
        fields.forEach(triple -> {
            stringBuilder.append("<").append(triple.getLeft()).append("> ").append(triple.getMiddle()).append(" ").append(triple.getRight()).append(".\r\n");
        });
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    protected String buildUpdateQuery(T entity) throws Exception {
        Map<String, String> dependencies = getDependencies();
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : dependencies.keySet()) {
            stringBuilder.append("PREFIX ").append(key).append(":")
                    .append(" <").append(dependencies.get(key)).append(">");
        }
        stringBuilder.append("\r\n")
                .append("INSERT DATA {").append("\r\n");
        List<Triple<String, String, String>> fields = getTriples(entity);
        fields.forEach(triple -> {
            stringBuilder.append("<").append(triple.getLeft()).append("> ").append(triple.getMiddle()).append(" ").append(triple.getRight()).append(".\r\n");
        });
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    protected String buildFindQuery(String variable, String value) throws Exception {
        Map<String, String> dependencies = getDependencies();
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : dependencies.keySet()) {
            stringBuilder.append("PREFIX ").append(key).append(":")
                    .append(" <").append(dependencies.get(key)).append(">");
        }
        stringBuilder.append("\r\n")
                .append("SELECT * WHERE {").append("\r\n");
        List<Triple<String, String, String>> fields = getFindTriples();
        fields.forEach(triple -> {
            stringBuilder.append("?").append(triple.getLeft()).append(" ").append(triple.getMiddle()).append(" ").append("?" + triple.getRight()).append(".\r\n");
        });
        stringBuilder.append("FILTER(str(?").append(variable).append("Value").append(") = \"").append(value).append("\")").append("\r\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
