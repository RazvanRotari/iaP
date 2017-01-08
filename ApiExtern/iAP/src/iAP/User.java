package iAP;

import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="users")
public class User {

	private int id;
	private String username;
	private String name;
	private String password;
	private String email;
	
	public User() {
	}

	public User(@JsonProperty(value= "id") int id,
			    @JsonProperty(value= "username",required = true) String username,
			    @JsonProperty(value= "name") String name, 
			    @JsonProperty(value= "password", required = true) String password,
			    @JsonProperty(value= "email") String email) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
