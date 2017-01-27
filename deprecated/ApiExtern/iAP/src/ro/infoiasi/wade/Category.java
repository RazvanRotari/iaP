package ro.infoiasi.wade;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "categories")
public class Category {
	
	@JsonProperty(value = "id")
	private int id;
	@JsonProperty(value = "name")
	private String name;

	public Category() {
	}
	
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
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
	
	@Override
    public String toString() {
    	return "Category: {id: "+getId()
    		+", name: "+getName();
    }
}
