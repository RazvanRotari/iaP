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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.infoiasi.dao.DAO;
import ro.infoiasi.dao.entity.Entity;
import ro.infoiasi.routes.user.UserLoginRote;
import ro.infoiasi.sparql.insertionPoints.*;
import ro.infoiasi.sparql.insertionPoints.Optional;
import ro.infoiasi.sparql.insertionPoints.subqueries.SubQuery;
import ro.infoiasi.sparql.prefixes.Prefix;
import ro.infoiasi.sparql.prefixes.annotations.OneToOne;
import ro.infoiasi.sparql.prefixes.annotations.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public abstract class GenericDAO<T extends Entity> implements DAO<T> {
    protected static final Logger logger = LoggerFactory.getLogger(GenericDAO.class);
    private int NA = -1;
    public static final String HTTP_ENDPOINT = "http://razvanrotari.me:3030/default";
    protected static final boolean DEBUG = true;

    protected final Class<T> clazz;
    private String mappedItems;

    public GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void create(T entity) throws Exception {
        String queryString = buildInsertQuery(entity);
        if (DEBUG) {
            logger.info(queryString);
        }
        UpdateRequest request = UpdateFactory.create(queryString);
        UpdateProcessRemote remote = new UpdateProcessRemote(request, HTTP_ENDPOINT, null);
        remote.execute();
    }

    public void update(T entity) throws Exception {
        delete(entity);
        create(entity);
    }

    public T find(QueryInsertionPoint... queryInsertionPoints) throws Exception {
        String findQuery = buildFindQuery(NA, queryInsertionPoints);
        if (DEBUG) {
            logger.info(findQuery);
        }
        ResultSet resultSet = QueryExecutionFactory.sparqlService(HTTP_ENDPOINT, findQuery).execSelect();
        if (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            T entity = toEntity(solution);
            addExtraProperties(solution, entity);
            return entity;
        }
        throw new Exception("Cannot find entity of type" + clazz + " with field: " + Arrays.toString(queryInsertionPoints));
    }

    private T addExtraProperties(QuerySolution solution, T entity) {
        for (Iterator<String> it = solution.varNames(); it.hasNext(); ) {
            String varname = it.next();
            if (!getMappedItems().contains(varname)) {
                if(solution.get(varname).isLiteral()) {
                    entity.put(varname, solution.getLiteral(varname).getString());
                } else {
                    entity.put(varname, solution.getResource(varname).getURI());
                }

            }
        }
        return entity;
    }

    @Override
    public List<T> findAll(QueryInsertionPoint... queryInsertionPoints) throws Exception {
        return findAll(NA, queryInsertionPoints);
    }

    @Override
    public List<T> findAll(int limit, QueryInsertionPoint... queryInsertionPoints) throws Exception {
        String findQuery = buildFindQuery(limit, queryInsertionPoints);
        if (DEBUG) {
            logger.info(findQuery);
        }
        ResultSet resultSet = QueryExecutionFactory.sparqlService(HTTP_ENDPOINT, findQuery).execSelect();
        List<T> results = new ArrayList<T>();
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            T entity = toEntity(solution);
            addExtraProperties(solution, entity);
            results.add(entity);
        }
        return results;
    }

    @Override
    public void delete(T entity) throws Exception {
        String deleteQuery = buildDeleteQuery(entity);
        UpdateRequest request = UpdateFactory.create(deleteQuery);
        UpdateProcessRemote remote = new UpdateProcessRemote(request, HTTP_ENDPOINT, null);
        remote.execute();
    }

    @Override
    public void delete(QueryInsertionPoint filter) throws Exception {
        T entity = find(filter);
        delete(entity);
    }

    protected abstract T toEntity(QuerySolution solution) throws Exception;

    protected Set<String> getPrefixes() {
        Set<String> dependencies = new TreeSet<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Prefix prefix = null;
            if (field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                prefix = property.prefix();
            } else if(field.isAnnotationPresent(OneToOne.class)) {
                OneToOne oneToOne = field.getAnnotation(OneToOne.class);
                prefix = oneToOne.prefix();
            }
            if(prefix != null) {
                String dependency = "PREFIX " + prefix.prefix + ": <" + prefix.url + ">";
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
            } else if(field.isAnnotationPresent(OneToOne.class)) {
                OneToOne oneToOne = field.getAnnotation(OneToOne.class);
                String getEntityMethod = "get" + StringUtils.capitalize(field.getName());
                Method method = clazz.getMethod(getEntityMethod);
                String subject = entity.getUniqueIdentifier();
                String property = oneToOne.prefix().prefix + ":" +oneToOne.field();
                Entity reffered = (Entity) method.invoke(entity);
                String value = reffered.getUniqueIdentifier();
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
                String subject = "resourceId";
                String property = prop.prefix().prefix + ":" + prop.field();
                String value = prop.variableName();
                Triple<String, String, String> triple =
                        new ImmutableTriple<>(subject, property, value);
                result.add(triple);
            } else if(field.isAnnotationPresent(OneToOne.class)) {
                OneToOne prop = field.getAnnotation(OneToOne.class);
                String subject = "resourceId";
                String property = prop.prefix().prefix + ":" + prop.field();
                String value = prop.variableName();
                Triple<String, String, String> triple =
                        new ImmutableTriple<>(subject, property, value);
                result.add(triple);
            }
        }
        return result;
    }

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
        stringBuilder.append("DELETE WHERE {").append("\r\n");
        stringBuilder.append("<").append(entity.getUniqueIdentifier()).append("> ").append("?p").append(" ").append("?v").append(";\r\n");
        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    protected String buildFindQuery(int limit, QueryInsertionPoint... queryInsertionPoints) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> prefixes = getPrefixes();
        for(QueryInsertionPoint insertionPoint: queryInsertionPoints) {
            if(insertionPoint instanceof Optional) {
                insertionPoint = ((Optional) insertionPoint).getInsertionPoint();
            }
            if(insertionPoint instanceof SubQuery) {
                SubQuery subQuery = (SubQuery) insertionPoint;
                subQuery.excludePrefixes();
                prefixes.addAll(subQuery.getPrefixes());
            }
        }
        for(String prefix: prefixes) {
            stringBuilder.append(prefix).append("\r\n");
        }
        stringBuilder.append("SELECT * WHERE {").append("\r\n");
        List<Triple<String, String, String>> fields = getFindTriples();
        fields.forEach(triple -> {
            stringBuilder.append("?").append(triple.getLeft()).append(" ").append(triple.getMiddle()).append(" ").append("?" + triple.getRight()).append(".\r\n");
        });
        for(QueryInsertionPoint queryInsertionPoint: queryInsertionPoints) {
            stringBuilder.append(queryInsertionPoint.construct()).append("\r\n");
        }
        stringBuilder.append("}");
        if(limit != NA) {
            stringBuilder.append(" limit ").append(limit);
        }
        return stringBuilder.toString();
    }

    public List<String> getMappedItems() {
        List<String> result = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                Property prop = field.getAnnotation(Property.class);
                result.add(prop.variableName());
            } else if(field.isAnnotationPresent(OneToOne.class)) {
                OneToOne prop = field.getAnnotation(OneToOne.class);
                result.add(prop.variableName());
            }
        }
        return result;
    }
}
