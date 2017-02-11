package ro.infoiasi.model.author;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ro.infoiasi.model.user.UserModel;

public class AuthorModel {
	@SerializedName("id")
	@Expose
	private long id;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("rating")
	@Expose
	private long rating;

	/**
	 * No args constructor for use in serialization
	 */
	public AuthorModel() {
	}

	/**
	 * @param id
	 * @param name
	 * @param rating
	 */
	public AuthorModel(long id, String name, long rating) {
		super();
		this.id = id;
		this.name = name;
		this.rating = rating;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

		public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getRating() {
		return rating;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(name).append(rating).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof AuthorModel) == false) {
			return false;
		}
		AuthorModel rhs = ((AuthorModel) other);
		return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).
				append(rating, rhs.rating).isEquals();
	}

}
