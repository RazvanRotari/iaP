package ro.infoiasi.wade;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "authors")
public class Author {
	
	@JsonProperty(value = "id")
	private int id;
	@JsonProperty(value = "name")
	private String name;
	@JsonProperty(value = "rating")
	private int rating;
	
	public Author()  {
		
	}
	
	public Author(int id, String name, int rating) {
		this.id=id;
		this.name=name;
		this.rating=rating;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	@Override
	public String toString() {
	    	return "Author: {id: "+getId()
	    		+", name: "+getName()
	    		+", rating: "+getRating();
	    }

}
