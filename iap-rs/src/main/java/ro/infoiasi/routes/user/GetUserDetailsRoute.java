package ro.infoiasi.routes.user;

import ro.infoiasi.dao.entity.metamodel.UserMetaModel;
import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetUserDetailsRoute implements Route{
    private UserDAO userDAO = new UserDAO();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.params(":name");
        User user = userDAO.find(new SingleFilter(new Equals(User.class, UserMetaModel.USERNAME_VALUE, Transformer.STR), name));
        return user;
    }

}
