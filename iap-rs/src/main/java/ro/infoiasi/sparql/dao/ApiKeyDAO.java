package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.dao.entity.ApiKey;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.IdentityTransformer;
import ro.infoiasi.sparql.insertionPoints.transformer.PropertyTransformer;

import java.util.*;

public class ApiKeyDAO extends GenericDAO<ApiKey>{
    public static final String MEMBER_ID = "memberId";
    public static final String EXPIRES_AT = "expiresAt";
    public static final String KEY_VALUE = "keyValue";
    private static final List<String> mappedItems = new ArrayList<>(
            Arrays.asList(MEMBER_ID, EXPIRES_AT, KEY_VALUE));


    private UserDAO userDAO = new UserDAO();
    public ApiKeyDAO() {
        super(ApiKey.class);
    }

    @Override
    protected ApiKey toEntity(QuerySolution solution) throws Exception {
        ApiKey apiKey = new ApiKey();
        String id = solution.getLiteral(MEMBER_ID).getString();
        apiKey.setUser(userDAO.find(new SingleFilter(new Equals(User.class, "id", IdentityTransformer.STR), id)));
        apiKey.setExpires(solution.getLiteral(EXPIRES_AT).getLong());
        apiKey.setKey(solution.getLiteral(KEY_VALUE).getString());
        return apiKey;
    }

    @Override
    public List<String> getMappedItems() {
        return mappedItems;
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
