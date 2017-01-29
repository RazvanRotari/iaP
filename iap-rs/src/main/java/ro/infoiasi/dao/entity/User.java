package ro.infoiasi.dao.entity;


import ro.infoiasi.Main;
import ro.infoiasi.sparql.prefixes.fields.FOAF_Fields;
import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.RR_Fields;

import static ro.infoiasi.dao.entity.metamodel.UserMetaModel.*;
import static ro.infoiasi.sparql.prefixes.Prefix.FOAF;
import static ro.infoiasi.sparql.prefixes.Prefix.RR;

public class User implements Entity {

    @Property(prefix= RR, field = RR_Fields.ID, variable = ID, variableName = ID_VALUE)
    private long id;
    @Property(prefix= FOAF, field = FOAF_Fields.ACCOUNT_NAME, variable = USERNAME, variableName = USERNAME_VALUE)
    private String userName;
    @Property(prefix= FOAF, field = FOAF_Fields.NAME, variable = NAME, variableName = NAME_VALUE)
    private String name;
    @Property(prefix= FOAF, field = FOAF_Fields.SHA_1, variable = HASH, variableName = HASH_VALUE)
    private String password;
    @Property(prefix= FOAF, field = FOAF_Fields.ONLINE_ACCOUNT, variable = EMAIL, variableName = EMAIL_VALUE)
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
        return Main.BASE_URL + "/user/" + id;
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
