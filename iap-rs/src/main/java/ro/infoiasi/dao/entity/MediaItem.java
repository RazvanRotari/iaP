package ro.infoiasi.dao.entity;


import ro.infoiasi.sparql.prefixes.Prefix;
import ro.infoiasi.sparql.prefixes.Property;
import ro.infoiasi.sparql.prefixes.RR_Fields;



public class MediaItem implements Entity {

    private String url;
    @Property(prefix = Prefix.RR, field = RR_Fields.empty, variable = "url")
    private String title;
    @Property(prefix = Prefix.RR, field = RR_Fields.empty, variable = "title")
    private String author;
    @Property(prefix = Prefix.RR, field = RR_Fields.empty, variable = "provider")
    private String provider;
    @Property(prefix = Prefix.RR, field = RR_Fields.empty, variable = "description")
    private String description;
    @Property(prefix = Prefix.RR, field = RR_Fields.empty, variable = "timestamp")
    private long timestamp;
    @Property(prefix = Prefix.RR, field = RR_Fields.empty, variable = "class")
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
