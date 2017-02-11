package ro.infoiasi.routes.user;

import org.apache.http.HttpStatus;
import ro.infoiasi.dao.entity.ApiKey;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.dao.entity.metamodel.UserMetaModel;
import ro.infoiasi.model.user.UserModel;
import ro.infoiasi.routes.RequestBodyParser;
import ro.infoiasi.sparql.dao.ApiKeyDAO;
import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.halt;

public class UpdateUserRoute implements Route {
    private UserDAO userDAO = new UserDAO();
    private ApiKeyDAO apiKeyDAO = new ApiKeyDAO();

    @Override
    public Object handle(Request request, Response response) throws Exception {
        UserModel uModel= new RequestBodyParser().parse(request.body(), UserModel.class);
        if(uModel== null) {
            halt(HttpStatus.SC_BAD_REQUEST, "Invalid body");
            return null;
        }
        String id = request.params(":id");
        User user = userDAO.find(new SingleFilter(new Equals(User.class, UserMetaModel.ID_VALUE, Transformer.STR), id));

        return null;
    }

    private String getAuthenticationToken(Request request) {
        return request.headers("Authorization");
    }

    private ApiKey getApiKeyInfo(String token) throws Exception {
        return apiKeyDAO.find(new SingleFilter(new Equals(ApiKey.class, "apiKey", Transformer.STR), token));
    }
}
