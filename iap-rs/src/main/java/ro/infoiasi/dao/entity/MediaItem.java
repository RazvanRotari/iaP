package ro.infoiasi.dao.entity;


import ro.infoiasi.sparql.prefixes.fields.DC_Fields;
import ro.infoiasi.sparql.prefixes.Prefix;
import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.RR_Fields;



public class MediaItem extends Entity {

    public static final String CLASS_TYPE = "class";
    public static final String ITEM_DESCRIPTION = "description";
    public static final String ITEM_TIMESTAMP = "timestamp";
    public static final String ITEM_TITLE = "title";

    private String url;
    @Property(prefix = Prefix.DC, field = DC_Fields.TITLE, variableName = "title")
    private String title;
    @Property(prefix = Prefix.DC, field = DC_Fields.DESCRIPTION, variableName = "description")
    private String description;
    @Property(prefix = Prefix.DC, field = DC_Fields.CREATED, variableName = "timestamp")
    private long timestamp;
    @Property(prefix = Prefix.RR, field = RR_Fields.CLASS_NAME, variableName = "class")
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

    @Override
    public String toString() {
        return "MediaItem{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                ", className='" + className + '\'' +
                ", extraProperties ='" + extraProperties + '\'' +
                '}';
    }
}
