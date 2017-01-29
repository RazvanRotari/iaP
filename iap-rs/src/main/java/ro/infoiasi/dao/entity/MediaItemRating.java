package ro.infoiasi.dao.entity;

import ro.infoiasi.dao.entity.metamodel.UserMetaModel;
import ro.infoiasi.sparql.prefixes.annotations.OneToOne;
import ro.infoiasi.sparql.prefixes.Prefix;
import ro.infoiasi.sparql.prefixes.annotations.Property;
import ro.infoiasi.sparql.prefixes.fields.RR_Fields;
import ro.infoiasi.sparql.prefixes.fields.SKOS_Field;

public class MediaItemRating extends Entity {

    @Property(prefix = Prefix.RR, field = RR_Fields.ID, variable= "id", variableName = "uid")
    private long id;
    @OneToOne(prefix = Prefix.RR, field = UserMetaModel.ID, variable = "user", variableName = "userId")
    private User user;
    @OneToOne(prefix = Prefix.RR, field = "id", variable = "mediaItem", variableName = "itemId")
    private MediaItem item;
    @Property(prefix = Prefix.SKOS, field = SKOS_Field.NOTE, variable = "score", variableName = "itemScore")
    private double score;


    public MediaItemRating() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MediaItem getItem() {
        return item;
    }

    public void setItem(MediaItem item) {
        this.item = item;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String getUniqueIdentifier() {
        return item.getUniqueIdentifier() + "?user=" + user.getId();
    }
}
