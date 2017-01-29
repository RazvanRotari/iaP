package ro.infoiasi.dao.entity;

import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
    protected Map<String, String> extraProperties = new HashMap<>();

    public abstract String getUniqueIdentifier();

    public void put(String property, String value) {
        extraProperties.put(property, value);
    }

    public String getProperty(String property) {
        return extraProperties.get(property);
    }
}
