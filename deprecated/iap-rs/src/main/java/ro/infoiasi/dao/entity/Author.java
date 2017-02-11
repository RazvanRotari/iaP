package ro.infoiasi.dao.entity;

import static ro.infoiasi.dao.entity.metamodel.AuthorMetaModel.*;
import static ro.infoiasi.sparql.prefixes.Prefix.FOAF;
import static ro.infoiasi.sparql.prefixes.Prefix.RR;

import ro.infoiasi.Main;
import ro.infoiasi.sparql.prefixes.Prefix;
import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.FOAF_Fields;
import ro.infoiasi.sparql.prefixes.fields.RR_Fields;
import ro.infoiasi.sparql.prefixes.fields.SKOS_Field;

public class Author extends Entity {

	@Property(prefix = RR, field = RR_Fields.ID, variableName = ID_VALUE)
	private long id;
	@Property(prefix = FOAF, field = FOAF_Fields.NAME, variableName = NAME_VALUE)
	private String name;
	@Property(prefix = Prefix.SKOS, field = SKOS_Field.NOTE, variableName = RATING)
	private long rating;

	public Author() {
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
	
	public void setRating(long string) {
		this.rating = string;
	}

	@Override
	public String getUniqueIdentifier() {
		return Main.BASE_URL + "/author/" + id;
	}
	
	@Override
	public String toString() {
	    	return "{ \"id\": \""+getId()
	    		+"\", \"name\": \""+getName()
	    		+"\", \"rating\": \""+getRating()+"\"}";
	    }

}
