package ro.infoiasi.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import ro.infoiasi.sparql.prefixes.FOAF_Fields;
import ro.infoiasi.sparql.prefixes.Property;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao extends GenericDAO{

    public void create(User user) throws Exception {
        Map<String,String> dependencies = getDependencies(user);
        StringBuilder stringBuilder = new StringBuilder();
        for(String key: dependencies.keySet()) {
            stringBuilder.append("PREFIX ").append(key).append(":")
                    .append(" <").append(dependencies.get(key)).append(">");
        }
        stringBuilder.append("\r\n")
                .append("INSERT DATA {").append("\r\n");
        List<Triple<String, String, String>> fields = getFields(user);
        fields.forEach(triple -> {
            stringBuilder.append("<").append(triple.getLeft()).append("> ").append(triple.getMiddle()).append(" ").append(triple.getRight()).append(".\r\n");
        });
        stringBuilder.append("}");

        String query = stringBuilder.toString();
        System.out.println(query);
        query(query);

    }

    public void update(User user) {
    }

    public User find(FOAF_Fields field, String value) {
        return new User();
    }


}
