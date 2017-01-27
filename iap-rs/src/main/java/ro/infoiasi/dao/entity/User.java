package ro.infoiasi.dao.entity;


import ro.infoiasi.sparql.prefixes.FOAF_Fields;
import ro.infoiasi.sparql.prefixes.Property;

import static ro.infoiasi.sparql.prefixes.Prefix.FOAF;

public class User implements Entity {

    @Property(prefix= FOAF, field = FOAF_Fields.OPEN_ID, variable = "id", variableName = "idValue")
    private long id;
    @Property(prefix= FOAF, field = FOAF_Fields.ACCOUNT_NAME, variable = "username", variableName = "usernameValue")
    private String userName;
    @Property(prefix= FOAF, field = FOAF_Fields.NAME, variable = "name", variableName = "nameValue")
    private String name;
    @Property(prefix= FOAF, field = FOAF_Fields.SHA_1, variable = "hash", variableName = "hashValue")
    private String password;
    @Property(prefix= FOAF, field = FOAF_Fields.ONLINE_ACCOUNT, variable = "email", variableName = "emailValue")
    private String email;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
    public String getUniqueIdentifier() {
        return "http://wade.razvanrotari.me/user/" + id;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
