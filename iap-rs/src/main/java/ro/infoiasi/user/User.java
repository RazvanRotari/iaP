package ro.infoiasi.user;


import ro.infoiasi.sparql.prefixes.FOAF_Fields;
import ro.infoiasi.sparql.prefixes.Property;

import static ro.infoiasi.sparql.prefixes.Prefix.FOAF;

public class User implements Entity {

    @Property(prefix= FOAF, field = FOAF_Fields.OPEN_ID)
    private long id;
    @Property(prefix= FOAF, field = FOAF_Fields.ACCOUNT_NAME)
    private String userName;
    @Property(prefix= FOAF, field = FOAF_Fields.NAME)
    private String name;
    @Property(prefix= FOAF, field = FOAF_Fields.SHA_1)
    private String password;

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

    @Override
    public String getUniqueIdentifier() {
        return "http://wade.razvanrotari.me/user/" + id;
    }
}
