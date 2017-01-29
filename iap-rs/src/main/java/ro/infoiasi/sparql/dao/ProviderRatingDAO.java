package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.ProviderRating;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.IdentityTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProviderRatingDAO extends GenericDAO<ProviderRating>{

    public static final String PROVIDER_SCORE = "providerScore";
    public static final String MEMBER_ID = "memberId";
    public static final String PROVIDER_ID = "providerId";
    public static final String UID = "uid";
    private static final List<String> mappedItems = new ArrayList<>(
            Arrays.asList(PROVIDER_ID,PROVIDER_SCORE, MEMBER_ID, UID));


    private UserDAO userDAO = new UserDAO();
    private ProviderDAO providerDAO = new ProviderDAO();

    public ProviderRatingDAO() {
        super(ProviderRating.class);
    }

    @Override
    protected ProviderRating toEntity(QuerySolution solution) throws Exception {
        ProviderRating rating = new ProviderRating();
        rating.setScore(solution.getLiteral(PROVIDER_SCORE).getDouble());
        String id = solution.getLiteral(MEMBER_ID).getString();
        rating.setUser(userDAO.find(new SingleFilter(new Equals(User.class, "id", IdentityTransformer.STR), id)));
        String mid = solution.getLiteral(PROVIDER_ID).getString();
        rating.setProvider(providerDAO.find(new SingleFilter(new Equals(User.class, "id", IdentityTransformer.STR), mid)));
        rating.setId(solution.getLiteral(UID).getLong());
        return rating;
    }

    @Override
    public List<String> getMappedItems() {
        return mappedItems;
    }
}
