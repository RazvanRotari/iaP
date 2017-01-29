package ro.infoiasi.model.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SearchQuery {

    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private String value;

    /**
     * No args constructor for use in serialization
     */
    public SearchQuery() {
    }

    /**
     * @param field
     * @param value
     * @param type
     */
    public SearchQuery(String field, String type, String value) {
        super();
        this.field = field;
        this.type = type;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SearchQuery withField(String field) {
        this.field = field;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SearchQuery withType(String type) {
        this.type = type;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public SearchQuery withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(field).append(type).append(value).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SearchQuery) == false) {
            return false;
        }
        SearchQuery rhs = ((SearchQuery) other);
        return new EqualsBuilder().append(field, rhs.field).append(type, rhs.type).append(value, rhs.value).isEquals();
    }

}