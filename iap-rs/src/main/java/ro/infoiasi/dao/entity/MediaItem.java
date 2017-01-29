package ro.infoiasi.dao.entity;


import ro.infoiasi.sparql.prefixes.fields.DC_Fields;
import ro.infoiasi.sparql.prefixes.Prefix;
import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.RR_Fields;



public class MediaItem implements Entity {

    @Property(prefix = Prefix.RR, field = RR_Fields.ID, variable = "url", variableName = "uid")
    private long id;
    @Property(prefix = Prefix.RR, field = RR_Fields.URL, variable = "url", variableName = "itemUrl")
    private String url;
    @Property(prefix = Prefix.DC, field = DC_Fields.TITLE, variable = "title", variableName = "itemTitle")
    private String title;
    @Property(prefix = Prefix.DC, field = DC_Fields.DESCRIPTION, variable = "description", variableName = "itemDescription")
    private String description;
    @Property(prefix = Prefix.DC, field = DC_Fields.DATE, variable = "timestamp", variableName = "itemTimestamp")
    private long timestamp;
    @Property(prefix = Prefix.DC, field = DC_Fields.TYPE, variable = "class", variableName = "classType")
    private String className;

    @Override
    public String getUniqueIdentifier() {
        return url;
    }

    public MediaItem() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
