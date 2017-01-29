package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.dao.entity.ApiKey;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.IdentityTransformer;
import ro.infoiasi.sparql.insertionPoints.transformer.PropertyTransformer;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ApiKeyDAO extends GenericDAO<ApiKey>{
    private UserDAO userDAO = new UserDAO();

    public ApiKeyDAO() {
        super(ApiKey.class);
    }

    @Override
    protected ApiKey toEntity(QuerySolution solution) throws Exception {
        ApiKey apiKey = new ApiKey();
        String id = solution.getLiteral("memberId").getString();
        apiKey.setUser(userDAO.find(new SingleFilter(new Equals(User.class, "id", IdentityTransformer.STR), id)));
        apiKey.setExpires(solution.getLiteral("expiresAt").getLong());
        apiKey.setKey(solution.getLiteral("keyValue").getString());
        return apiKey;
    }

    public ApiKey findByUser(User user) throws Exception {
        return find(new SingleFilter(new Equals(User.class, "id", PropertyTransformer.STR), user.getUniqueIdentifier()));
    }

    public ApiKey createAuthToken(User user) {
        ApiKey apiKey = new ApiKey();
        apiKey.setUser(user);
        apiKey.setKey(UUID.randomUUID().toString());
        apiKey.setExpires(getExpirationDate());
        return apiKey;
    }

    public User findUserByKey(String apiKey) throws Exception {
        ApiKey auth = find(new SingleFilter(new Equals(User.class, "apiKey", PropertyTransformer.STR), apiKey));
        return auth.getUser();
    }

    public long getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 10);
        return calendar.getTimeInMillis();
    }
}
