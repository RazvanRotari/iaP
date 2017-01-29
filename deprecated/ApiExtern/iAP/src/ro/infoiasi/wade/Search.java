package ro.infoiasi.wade;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "queries")
public class Search {
	
	@JsonProperty(value="field")
	private String field;
	@JsonProperty(value="type")
	private String type;
	@JsonProperty(value="value")
	private String value;
	
	public Search() {
		
	}
	
	public Search(String field,String type, String value) {
		this.field=field;
		this.type=type;
		this.value=value;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	 @Override
	    public String toString() {
	    	return "Query: {field: "+getField()
	    		+", type: "+getType()
	    		+", value: "+getValue()+" }";
	    }
}
