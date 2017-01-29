package ro.infoiasi.dao.entity;

import ro.infoiasi.sparql.prefixes.annotations.OneToOne;
import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.RR_Fields;
import ro.infoiasi.sparql.prefixes.fields.SKOS_Field;

import static ro.infoiasi.sparql.prefixes.Prefix.RR;
import static ro.infoiasi.sparql.prefixes.Prefix.SKOS;

public class ApiKey extends Entity {
    @Property(prefix = RR, field = RR_Fields.KEY, variable = "apiKey", variableName = "keyValue")
    private String key;
    @OneToOne(prefix = SKOS, field = SKOS_Field.MEMBER, variable = "member", variableName = "memberId")
    private User user;
    @Property(prefix = RR, field = RR_Fields.EXPIRES, variable = "expires", variableName = "expiresAt")
    private long expires;

    public ApiKey() {
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getUniqueIdentifier() {
        return "ApiKey." + user.getId();
    }
}
