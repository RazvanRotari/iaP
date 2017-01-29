package ro.infoiasi.routes.user;

import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.predicate.PropertyTransformer;
import spark.Request;
import spark.Response;
import spark.Route;

public class ShowUserDetailsRoute implements Route{
    private UserDAO userDAO = new UserDAO();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.params(":name");
        User user = userDAO.find(new SingleFilter(new Equals(User.class, "username", PropertyTransformer.STR), name));
        return null;
    }

}
