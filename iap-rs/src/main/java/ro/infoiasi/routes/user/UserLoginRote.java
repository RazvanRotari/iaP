package ro.infoiasi.routes.user;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.infoiasi.dao.entity.ApiKey;
import ro.infoiasi.dao.entity.metamodel.UserMetaModel;
import ro.infoiasi.sparql.dao.ApiKeyDAO;
import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.model.user.Credentials;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.PropertyTransformer;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Calendar;
import java.util.Date;

import static spark.Spark.halt;

public class UserLoginRote implements Route {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginRote.class);
    private Gson gson = new Gson();
    private UserDAO userDAO;
    private ApiKeyDAO apiKeyDAO;

    public UserLoginRote() {
        userDAO = new UserDAO();
        apiKeyDAO = new ApiKeyDAO();
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Credentials credentials = getCredentials(request.body());
        if(credentials == null) {
            halt(HttpStatus.SC_BAD_REQUEST, "No credentials found");
            return null;
        }
        Equals stringEqualsPredicate = new Equals(User.class, UserMetaModel.USERNAME, PropertyTransformer.STR);
        User user = userDAO.find(new SingleFilter(stringEqualsPredicate, credentials.getUsername()));
        if(user.getPassword().equals(credentials.getPassword())) {
            ApiKey apiKey = apiKeyDAO.findByUser(user);
            if(isExpired(apiKey)) {
                apiKeyDAO.delete(apiKey);
                apiKey = apiKeyDAO.createAuthToken(user);
                return apiKey.getKey();
            }
        } else {
            halt(HttpStatus.SC_UNAUTHORIZED, "Invalid credentials");
            return null;
        }
        return null;
    }


    private boolean isExpired(ApiKey apiKey) {
        return apiKey.getExpires() < getExpirationDate();
    }

    public long getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 10);
        return calendar.getTimeInMillis();
    }

    private Credentials getCredentials(String requestBody) {
        try {
            return gson.fromJson(requestBody, Credentials.class);
        }catch (JsonSyntaxException jse) {
            logger.error("Could not parse credentials", jse);
            return null;
        }
    }
}
