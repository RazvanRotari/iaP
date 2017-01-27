package ro.infoiasi.routes.user;

import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.sparql.filter.SingleFilter;
import ro.infoiasi.sparql.predicate.Equals;
import ro.infoiasi.sparql.predicate.Transformer;
import ro.infoiasi.views.UserViewModel;
import spark.Request;
import spark.Response;
import spark.Route;

public class ShowUserDetailsRoute implements Route{
    private UserDAO userDAO = new UserDAO(User.class);

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.params(":name");
        User user = userDAO.find(new SingleFilter(new Equals(User.class, "username", Transformer.STR), name));
        return toUsUserModel(user);
    }

    private UserViewModel toUsUserModel(User user) {
        UserViewModel userModel = new UserViewModel();
        userModel.setEmail(user.getEmail());
        userModel.setUserName(user.getUserName());
        userModel.setName(user.getName());
        return userModel;
    }
}
