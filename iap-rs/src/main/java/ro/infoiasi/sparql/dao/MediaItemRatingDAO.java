package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.MediaItemRating;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.dao.entity.metamodel.UserMetaModel;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;

public class MediaItemRatingDAO extends GenericDAO<MediaItemRating> {

    private UserDAO userDAO = new UserDAO();
    private MediaItemDAO mediaDao = new MediaItemDAO();

    public MediaItemRatingDAO() {
        super(MediaItemRating.class);
    }

    @Override
    protected MediaItemRating toEntity(QuerySolution solution) throws Exception {
        MediaItemRating rating = new MediaItemRating();
        rating.setScore(solution.getLiteral("score").getDouble());
        String id = solution.getLiteral("userId").getString();
        rating.setUser(userDAO.find(new SingleFilter(new Equals(User.class, UserMetaModel.ID_VALUE, Transformer.STR), id)));
        String mid = solution.getLiteral("itemId").getString();
        rating.setItem(mediaDao.find(new SingleFilter(new Equals(User.class, UserMetaModel.ID_VALUE, Transformer.STR), mid)));
        rating.setId(solution.getLiteral("uid").getLong());
        return rating;
    }
}
