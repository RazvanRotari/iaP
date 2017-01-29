package ro.infoiasi.dao.entity;

import ro.infoiasi.sparql.prefixes.*;
import ro.infoiasi.sparql.prefixes.annotations.OneToOne;
import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.DC_Fields;
import ro.infoiasi.sparql.prefixes.fields.RR_Fields;
import ro.infoiasi.sparql.prefixes.fields.SKOS_Field;

public class ProviderRating extends Entity {

    @Property(prefix = Prefix.RR, field = RR_Fields.ID, variable= "id", variableName = "uid")
    private long id;
    @OneToOne(prefix = Prefix.SKOS, field = SKOS_Field.MEMBER, variable = "member", variableName = "memberId")
    private User user;
    @OneToOne(prefix = Prefix.DC, field = DC_Fields.PROVENANCE, variable = "provider", variableName = "providerId")
    private Provider provider;
    @Property(prefix = Prefix.SKOS, field = SKOS_Field.NOTE, variable = "score", variableName = "providerScore")
    private double score;

    public ProviderRating() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String getUniqueIdentifier() {
        return provider.getUniqueIdentifier() + "?userID=" + user.getId();
    }

    @Override
    public String toString() {
        return "ProviderRating{" +
                "id=" + id +
                ", user=" + user +
                ", provider=" + provider +
                ", score=" + score +
                '}';
    }
}
