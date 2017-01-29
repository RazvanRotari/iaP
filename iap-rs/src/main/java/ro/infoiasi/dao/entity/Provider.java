package ro.infoiasi.dao.entity;

import ro.infoiasi.sparql.prefixes.*;
import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.DC_Fields;
import ro.infoiasi.sparql.prefixes.fields.RR_Fields;

import static ro.infoiasi.Main.BASE_URL;

public class Provider extends Entity {

    @Property(prefix = Prefix.RR, field = RR_Fields.ID, variable = "id", variableName = "providerID")
    private long id;
    @Property(prefix = Prefix.DC, field = DC_Fields.CREATOR, variable = "creator", variableName = "creatorName")
    private String author;
    @Property(prefix = Prefix.DC, field = DC_Fields.PUBLISHER, variable = "provider", variableName = "providerName")
    private String provider;

    public Provider() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String getUniqueIdentifier() {
        return BASE_URL + "/author/" + id;
    }
}
