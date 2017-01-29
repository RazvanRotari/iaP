package ro.infoiasi.routes.user;

import org.apache.http.HttpStatus;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.model.user.UserModel;
import ro.infoiasi.routes.RequestBodyParser;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.halt;

public class UpdateUserRoute implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        UserModel uModel= new RequestBodyParser().parse(request.body(), UserModel.class);
        if(uModel== null) {
            halt(HttpStatus.SC_BAD_REQUEST, "Invalid body");
            return null;
        }

        return null;
    }
}
