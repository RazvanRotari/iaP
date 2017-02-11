package ro.infoiasi.dao.entity;

import static ro.infoiasi.dao.entity.metamodel.CategoryMetaModel.NAME_VALUE;
import static ro.infoiasi.sparql.prefixes.Prefix.FOAF;

import ro.infoiasi.Main;
import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.FOAF_Fields;

public class Category extends Entity {

	@Property(prefix = FOAF, field = FOAF_Fields.NAME, variableName = NAME_VALUE)
	private String name;
	
	public Category() {
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getUniqueIdentifier() {
		return Main.BASE_URL + "/category/" + name;
	}
	
	@Override
    public String toString() {
    	return "{ \"name\": \""+getName()+"\"}";
    }

}
