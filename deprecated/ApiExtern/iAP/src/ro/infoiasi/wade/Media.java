package ro.infoiasi.wade;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "media")
public class Media {

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
	@JsonProperty(value = "url")
	private String url;
	@JsonProperty(value = "date")
	private String date;
	@JsonProperty(value = "externalURL")
	private String externalURL;
	@JsonProperty(value = "authorRating")
	private int authorRating;
	@JsonProperty(value = "mediaRating")
	private int mediaRating;
	
	public Media() {
		
	}
	
	public Media(int id, Category categ, String title, String language, String description, String url, String date, String externalURL, int authorRating, int mediaRating) {
		this.id=id;
		this.category=categ;
		this.title=title;
		this.language=language;
		this.description=description;
		this.url=url;
		this.date=date;
		this.externalURL=externalURL;
		this.authorRating=authorRating;
		this.mediaRating=mediaRating;
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
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
	
	public int getAuthorRating() {
		return authorRating;
	}
	
	public void setAuthorRating(int rating) {
		this.authorRating = rating;
	}
	
	public int getMediaRating() {
		return mediaRating;
	}
	
	public void setMediaRating(int mediaRating) {
		this.mediaRating = mediaRating;
	}
	
	@Override
    public String toString() {
    	return "{\"id\": \""+getId()
    		+"\", \"categoryName\": \""+getCateg().getName()
    		+"\", \"title\": \""+getTitle()
    		+"\", \"description\": \""+getDescription()
    		+"\", \"url\": \""+getUrl()
    		+"\", \"language\": \""+getLanguage()
    		+"\", \"date\": \""+getDate()
    		+"\", \"externalURL\": \""+getExternalURL()
    		+"\", \"authorRating\": \""+getAuthorRating()
    		+"\", \"mediaRating\":\""+getMediaRating()+"\"}";
    }
}
