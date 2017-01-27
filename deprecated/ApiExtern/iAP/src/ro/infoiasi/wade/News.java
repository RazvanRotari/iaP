package ro.infoiasi.wade;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "news")
public class News {

	@JsonProperty(value = "id")
	private int id;
	@JsonProperty(value = "category")
	private Category category;
	@JsonProperty(value = "title")
	private String title;
	@JsonProperty(value = "language")
	private String language;
	@JsonProperty(value = "description")
	private String description;
	@JsonProperty(value = "date")
	private String date;
	@JsonProperty(value = "externalURL")
	private String externalURL;
	
	public News() {
		
	}
	
	public News(int id, Category categ, String title, String language, String description, String date, String externalURL) {
		this.id=id;
		this.category=categ;
		this.title=title;
		this.language=language;
		this.description=description;
		this.date=date;
		this.externalURL=externalURL;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Category getCateg() {
		return category;
	}
	
	public void setCateg(Category categ) {
		this.category = categ;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getExternalURL() {
		return externalURL;
	}
	
	public void setExternalURL(String externalURL) {
		this.externalURL = externalURL;
	}
	
	@Override
    public String toString() {
    	return "News: {id: "+getId()
    		+", categoryName: "+getCateg().getName()
    		+", title: "+getTitle()
    		+", description: "+getDescription()
    		+", language: "+getLanguage()
    		+", date: "+getDate()
    		+", externalURL: "+getExternalURL();
    }
}
