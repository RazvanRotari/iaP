package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.dao.entity.ApiKey;
import ro.infoiasi.dao.entity.metamodel.UserMetaModel;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;

import java.util.*;

public class ApiKeyDAO extends GenericDAO<ApiKey>{
    public static final String MEMBER_ID = "memberId";
    public static final String EXPIRES_AT = "expiresAt";
    public static final String KEY_VALUE = "keyValue";

    private UserDAO userDAO = new UserDAO();
    public ApiKeyDAO() {
        super(ApiKey.class);
    }

    @Override
    protected ApiKey toEntity(QuerySolution solution) throws Exception {
        ApiKey apiKey = new ApiKey();
        String id = solution.getLiteral(MEMBER_ID).getString();
        apiKey.setUser(userDAO.find(new SingleFilter(new Equals(User.class, UserMetaModel.ID_VALUE, Transformer.STR), id)));
        apiKey.setExpires(solution.getLiteral(EXPIRES_AT).getLong());
        apiKey.setKey(solution.getLiteral(KEY_VALUE).getString());
        return apiKey;
    }

    public ApiKey findByUser(User user) throws Exception {
        return find(new SingleFilter(new Equals(User.class, UserMetaModel.ID_VALUE, Transformer.STR), user.getUniqueIdentifier()));
    }

    public ApiKey createAuthToken(User user) {
        ApiKey apiKey = new ApiKey();
        apiKey.setUser(user);
        apiKey.setKey(UUID.randomUUID().toString());
        apiKey.setExpires(getExpirationDate());
        return apiKey;
    }

    public User findUserByKey(String apiKey) throws Exception {
        ApiKey auth = find(new SingleFilter(new Equals(ApiKey.class, "keyValue", Transformer.STR), apiKey));
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
