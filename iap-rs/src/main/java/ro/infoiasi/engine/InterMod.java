package ro.infoiasi.engine;

import java.util.Date;
import java.util.List;

public class InterMod {
    private long id;
    private List<String> categories;
    private String title;
    private String language;
    private String description;
    private String url;
    private Date date;
    private String externalURL;
    private long authorRating;
    private long mediaRating;

    private double score;

    public InterMod() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }

    public long getAuthorRating() {
        return authorRating;
    }

    public void setAuthorRating(long authorRating) {
        this.authorRating = authorRating;
    }

    public long getMediaRating() {
        return mediaRating;
    }

    public void setMediaRating(long mediaRating) {
        this.mediaRating = mediaRating;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
