package ro.infoiasi.wade;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "categories")
public class Category {
	
	@JsonProperty(value = "name")
	private String name;

	public Category() {
	}
	
	public Category(String name) {
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
    public String toString() {
    	return "Category: { name: "+getName();
    }
}
