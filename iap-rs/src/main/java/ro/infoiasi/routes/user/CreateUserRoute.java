package ro.infoiasi.routes.user;

import static spark.Spark.halt;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.infoiasi.dao.entity.User;
import ro.infoiasi.model.user.UserModel;
import ro.infoiasi.routes.RequestBodyParser;
import ro.infoiasi.sparql.dao.UserDAO;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateUserRoute implements Route {
    private UserDAO userDAO = new UserDAO();
    private static final Logger logger = LoggerFactory.getLogger(UserLoginRoute.class);


    @Override
    public Object handle(Request request, Response response) {
        try {
            UserModel uModel = new RequestBodyParser().parse(request.body(), UserModel.class);
            if (uModel == null) {
                halt(HttpStatus.SC_BAD_REQUEST, "Invalid body");
                return null;
            }
            User user = toUser(uModel);
            if (user == null) {
                halt(HttpStatus.SC_NOT_IMPLEMENTED, "Problems with creating the user");
                return null;
            }
            if (user.getUserName() == null) {
    			halt(HttpStatus.SC_BAD_REQUEST,"Please provide username!!");
    			return null;
    		}
    		if (user.getPassword() == null) {
    			halt(HttpStatus.SC_BAD_REQUEST,"Please provide password!!");
    			return null;
    		}
    		if (!isUsernameUnique(user.getUserName())) {
    			halt(HttpStatus.SC_CONFLICT, "Account already exists");
    			return null;
    		}
            userDAO.create(user);
        } catch (Exception ex) {
            logger.error("Could not create user", ex);
        }
        return HttpStatus.SC_CREATED;
    }
    
    private boolean isUsernameUnique(String username) {
		for (User u : userDAO.userList) {
			if (u.getUserName().equals(username))
				return false;
		}
		return true;
	}

    private User toUser(UserModel userModel) throws Exception {
        try {
        	if(userModel.getId()!=0) {
        		halt(HttpStatus.SC_BAD_REQUEST, "Id should not be in the request body!");
        		return null;
        	}
            User user = new User();
            user.setName(userModel.getName());
            user.setUserName(userModel.getUsername());
            user.setEmail(userModel.getEmail());
            user.setPassword(hash(userModel.getPassword()));
            user.setId(userDAO.getNextId());
            return user;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private String hash(String password) throws NoSuchAlgorithmException {
        return DigestUtils.sha1Hex(password);
    }
}
