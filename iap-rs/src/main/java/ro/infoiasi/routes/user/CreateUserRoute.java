package ro.infoiasi.routes.user;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.model.user.UserModel;
import spark.Request;
import spark.Response;
import spark.Route;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static spark.Spark.halt;

public class CreateUserRoute implements Route {
    private UserDAO userDAO = new UserDAO();
    private static final Logger logger = LoggerFactory.getLogger(UserLoginRote.class);
    private Gson gson = new Gson();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        UserModel uModel= getUserDetails(request.body());
        if(uModel== null) {
            halt(HttpStatus.SC_BAD_REQUEST, "Invalid body");
            return null;
        }
        User user = toUser(uModel);
        if(user == null) {
            halt(HttpStatus.SC_NOT_IMPLEMENTED, "Problems with creating the user");
            return null;
        }
        userDAO.create(user);
        return 200;
    }

    private User toUser(UserModel userModel) {
        try {
            User user = new User();
            user.setName(userModel.getName());
            user.setUserName(userModel.getUsername());
            user.setEmail(userModel.getEmail());
            user.setPassword(hash(userModel.getPassword()));
            user.setId(userDAO.getNextId());
            return user;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UserModel getUserDetails(String requestBody) {
        try {
            return gson.fromJson(requestBody, UserModel.class);
        }catch (JsonSyntaxException jse) {
            logger.error("Could not parse credentials", jse);
            return null;
        }
    }

    private String hash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA-1");
        return new String(md.digest(password.getBytes()));
    }
}
