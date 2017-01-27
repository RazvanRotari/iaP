package ro.infoiasi.wade;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "users")
public class User {
    
	@JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "username", required = true)
    private String username;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "password", required = true)
    private String password;
    @JsonProperty(value = "email")
    private String email;

    public User() {
    }

    public User(int id, String username, String name, String password, String email) {
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
    
    @Override
    public String toString() {
    	return "User: {id: "+getId()
    		+", username: "+getUsername()
    		+", name: "+getName()
    		+", password: "+getPassword()
    		+", email: "+getEmail();
    }
}
