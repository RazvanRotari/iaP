package ro.infoiasi.views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import ro.infoiasi.model.categories.Category;

public class SearchResult {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("externalURL")
    @Expose
    private String externalURL;
    @SerializedName("authorRating")
    @Expose
    private long authorRating;
    @SerializedName("mediaRating")
    @Expose
    private long mediaRating;

    /**
     * No args constructor for use in serialization
     *
     */
    public SearchResult() {
    }

    /**
     *
     * @param id
     * @param mediaRating
     * @param title
     * @param category
     * @param externalURL
     * @param description
     * @param authorRating
     * @param language
     * @param date
     * @param url
     */
    public SearchResult(long id, Category category, String title, String language, String description, String url, String date, String externalURL, long authorRating, long mediaRating) {
        super();
        this.id = id;
        this.category = category;
        this.title = title;
        this.language = language;
        this.description = description;
        this.url = url;
        this.date = date;
        this.externalURL = externalURL;
        this.authorRating = authorRating;
        this.mediaRating = mediaRating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SearchResult withId(long id) {
        this.id = id;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SearchResult withCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SearchResult withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public SearchResult withLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SearchResult withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SearchResult withUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SearchResult withDate(String date) {
        this.date = date;
        return this;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }

    public SearchResult withExternalURL(String externalURL) {
        this.externalURL = externalURL;
        return this;
    }

    public long getAuthorRating() {
        return authorRating;
    }

    public void setAuthorRating(long authorRating) {
        this.authorRating = authorRating;
    }

    public SearchResult withAuthorRating(long authorRating) {
        this.authorRating = authorRating;
        return this;
    }

    public long getMediaRating() {
        return mediaRating;
    }

    public void setMediaRating(long mediaRating) {
        this.mediaRating = mediaRating;
    }

    public SearchResult withMediaRating(long mediaRating) {
        this.mediaRating = mediaRating;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(category).append(title).append(language).append(description).append(url).append(date).append(externalURL).append(authorRating).append(mediaRating).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SearchResult) == false) {
            return false;
        }
        SearchResult rhs = ((SearchResult) other);
        return new EqualsBuilder().append(id, rhs.id).append(category, rhs.category).append(title, rhs.title).append(language, rhs.language).append(description, rhs.description).append(url, rhs.url).append(date, rhs.date).append(externalURL, rhs.externalURL).append(authorRating, rhs.authorRating).append(mediaRating, rhs.mediaRating).isEquals();
    }

}