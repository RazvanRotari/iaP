package ro.infoiasi.routes.user;

import static spark.Spark.halt;

import org.apache.http.HttpStatus;

import ro.infoiasi.dao.entity.User;
import ro.infoiasi.dao.entity.metamodel.UserMetaModel;
import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.predicate.Transformer;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeleteUserRoute implements Route {

	private UserDAO userDAO;
	
	public DeleteUserRoute() {
        userDAO = new UserDAO();
    }
	
    @Override
    public Object handle(Request request, Response response) throws Exception {
    	String username = request.params(":username");
    	 Equals stringEqualsPredicate = new Equals(User.class, UserMetaModel.USERNAME, Transformer.STR);
         User user = userDAO.find(new SingleFilter(stringEqualsPredicate, username));
         if(user.getUserName().equals(username)) {
             userDAO.delete(user);
        	 return 204;
         } else {
             halt(HttpStatus.SC_NOT_FOUND, "User "+username+" not found");
             return 404;
         }
    }
}
