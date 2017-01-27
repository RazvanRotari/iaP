package ro.infoiasi.sparql.dao;

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
import ro.infoiasi.dao.DAO;
import ro.infoiasi.dao.entity.Entity;
import ro.infoiasi.sparql.filter.Filter;
import ro.infoiasi.sparql.prefixes.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public abstract class GenericDAO<T extends Entity> implements DAO<T> {

    public static final String HTTP_ENDPOINT = "http://razvanrotari.me:3030/default";
    protected static final boolean DEBUG = true;

    private final Class<T> clazz;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void create(T entity) throws Exception {
        String queryString = buildInsertQuery(entity);
        if (DEBUG) {
            System.out.println(queryString);
        }
        UpdateRequest request = UpdateFactory.create(queryString);
        UpdateProcessRemote remote = new UpdateProcessRemote(request, HTTP_ENDPOINT, null);
        remote.execute();
    }

    public void update(T entity) throws Exception {
        delete(entity);
        create(entity);
    }

    public T find(Filter filter) throws Exception {
        String findQuery = buildFindQuery(filter);
        if (DEBUG) {
            System.out.println(findQuery);
        }
        ResultSet resultSet = QueryExecutionFactory.sparqlService(HTTP_ENDPOINT, findQuery).execSelect();
        if (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            return toEntity(solution);
        }
        throw new NotFoundException("Cannot find entity of type" + clazz + " with field: " + filter);
    }

    @Override
    public void delete(T entity) throws Exception {
        String deleteQuery = buildDeleteQuery(entity);
        UpdateRequest request = UpdateFactory.create(deleteQuery);
        UpdateProcessRemote remote = new UpdateProcessRemote(request, HTTP_ENDPOINT, null);
        remote.execute();
    }

    @Override
    public void delete(Filter filter) throws Exception {
        T entity = find(filter);
        delete(entity);
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

    /**
     *
     * @param entity
     * @return
     * @throws Exception
     */
    protected List<Triple<String, String, String>> getTriples(T entity) throws Exception {
        List<Triple<String, String, String>> result = new ArrayList<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
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
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                Property prop = field.getAnnotation(Property.class);
                String subject = prop.variable();
                String property = prop.prefix().prefix + ":" + prop.field();
                String value = prop.variableName();
                Triple<String, String, String> triple =
                        new ImmutableTriple<>(subject, property, value);
                result.add(triple);
            }
        }
        return result;
    }

    protected abstract T toEntity(QuerySolution solution);

    protected String buildInsertQuery(T entity) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> prefixes = getPrefixes();
        for(String prefix: prefixes) {
            stringBuilder.append(prefix).append("\r\n");
        }
        stringBuilder.append("INSERT DATA {").append("\r\n");
        List<Triple<String, String, String>> fields = getTriples(entity);
        fields.forEach(triple -> {
            stringBuilder.append("<").append(triple.getLeft()).append("> ").append(triple.getMiddle()).append(" ").append(triple.getRight()).append(".\r\n");
        });
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    protected String buildDeleteQuery(T entity) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> prefixes = getPrefixes();
        for(String prefix: prefixes) {
            stringBuilder.append(prefix).append("\r\n");
        }
        stringBuilder.append("DELETE {").append("\r\n");
        List<Triple<String, String, String>> fields = getTriples(entity);
        fields.forEach(triple -> {
            stringBuilder.append("<").append(triple.getLeft()).append("> ").append(triple.getMiddle()).append(" ").append(triple.getRight()).append(".\r\n");
        });
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    protected String buildFindQuery(Filter filter) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> prefixes = getPrefixes();
        for(String prefix: prefixes) {
            stringBuilder.append(prefix).append("\r\n");
        }
        stringBuilder.append("SELECT * WHERE {").append("\r\n");
        List<Triple<String, String, String>> fields = getFindTriples();
        fields.forEach(triple -> {
            stringBuilder.append("?").append(triple.getLeft()).append(" ").append(triple.getMiddle()).append(" ").append("?" + triple.getRight()).append(".\r\n");
        });
        stringBuilder.append(filter.construct()).append("\r\n");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
