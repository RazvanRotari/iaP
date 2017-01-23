package ro.infoiasi.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.jena.query.*;
import org.apache.jena.sparql.modify.UpdateProcessRemote;
import org.apache.jena.sparql.modify.request.UpdateCreate;
import org.apache.jena.update.UpdateAction;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import ro.infoiasi.sparql.prefixes.Property;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericDAO {

    public static final String HTTP_ENDPOINT = "http://razvanrotari.me:3030/default";

    protected Map<String, String> getDependencies(Entity entity) throws Exception {
        Map<String, String> map = new HashMap<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Property.class)) {
                Property property = field.getAnnotation(Property.class);
                String prefix = property.prefix().prefix;
                String url = property.prefix().url;
                map.put(prefix, url);
            } else {
                throw new Exception("Field not annotated");
            }
        }
        return map;
    }

    protected List<Triple<String, String, String>> getFields(Entity entity) {
        List<Triple<String, String, String>> result = new ArrayList<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for(Field field: fields) {
            if(field.isAnnotationPresent(Property.class)) {
                Property prop = field.getAnnotation(Property.class);
                String subject = entity.getUniqueIdentifier();
                String property = prop.prefix().prefix + ":" + prop.field();
                String value = "<NULL>";
                try {
                    Method method = entity.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
                    Object o = method.invoke(entity);
                    value = String.valueOf(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Triple<String, String, String> triple =
                        new ImmutableTriple<>(subject, property, "\"" + value + "\"");
                result.add(triple);
            }
        }
        return result;
    }

    protected void query(String queryString) {
        UpdateRequest request = UpdateFactory.create(queryString);
        UpdateProcessRemote remote = new UpdateProcessRemote(request, HTTP_ENDPOINT, null);
        remote.execute();
    }
}
