package ro.infoiasi.dao.entity;

import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.DC_Fields;

import static ro.infoiasi.dao.entity.metamodel.SearchMetaModel.*;
import static ro.infoiasi.sparql.prefixes.Prefix.DC;

import ro.infoiasi.Main;

public class Search extends Entity{
	
	@Property(prefix= DC, field=DC_Fields.TITLE, variableName= FIELD_VALUE)
	private String field;
	@Property(prefix= DC, field=DC_Fields.TYPE, variableName= TYPE_VALUE)
	private String type;
	@Property(prefix= DC, field=DC_Fields.DESCRIPTION, variableName= VALUE_VALUE)
	private String value;
	
	public Search() {
		
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
	
	//Un search nu ar trebui sa aiba identificator unic
	@Override
	public String getUniqueIdentifier() {
		return Main.BASE_URL+"/search/";
	}
	
	@Override
    public String toString() {
        return "{ \"field\" : \"" + getField() +
                "\", \"type\":\" " + getType() + 
                ", \" value \":\"" + getValue() + "\"}";
	}
}
