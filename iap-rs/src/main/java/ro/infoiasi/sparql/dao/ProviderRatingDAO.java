package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.ProviderRating;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.IdentityTransformer;

public class ProviderRatingDAO extends GenericDAO<ProviderRating>{
    private UserDAO userDAO = new UserDAO();
    private ProviderDAO providerDAO = new ProviderDAO();

    public ProviderRatingDAO() {
        super(ProviderRating.class);
    }

    @Override
    protected ProviderRating toEntity(QuerySolution solution) throws Exception {
        ProviderRating rating = new ProviderRating();
        rating.setScore(solution.getLiteral("providerScore").getDouble());
        String id = solution.getLiteral("memberId").getString();
        rating.setUser(userDAO.find(new SingleFilter(new Equals(User.class, "id", IdentityTransformer.STR), id)));
        String mid = solution.getLiteral("providerId").getString();
        rating.setProvider(providerDAO.find(new SingleFilter(new Equals(User.class, "id", IdentityTransformer.STR), mid)));
        rating.setId(solution.getLiteral("uid").getLong());
        return rating;
    }
}
