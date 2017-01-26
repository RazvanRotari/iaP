package ro.infoiasi.routes.user;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.model.Credentials;
import ro.infoiasi.sparql.filter.SingleFilter;
import ro.infoiasi.sparql.predicate.Equals;
import ro.infoiasi.sparql.predicate.Transformer;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.halt;

public class UserLoginRote implements Route {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginRote.class);
    private Gson gson = new Gson();
    private UserDAO userDAO;

    public UserLoginRote() {
        userDAO = new UserDAO(User.class);
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Credentials credentials = getCredentials(request.body());
        if(credentials == null) {
            halt(HttpStatus.SC_BAD_REQUEST, "No credentials found");
            return null;
        }
        Equals stringEqualsPredicate = new Equals("usernameValue", Transformer.STR);
        User user = userDAO.find(new SingleFilter(stringEqualsPredicate, credentials.getUsername()));
        if(user.getPassword().equals(credentials.getPassword())) {
            return user.getId();
        } else {
            halt(HttpStatus.SC_UNAUTHORIZED, "Invalid credentials");
            return null;
        }
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
