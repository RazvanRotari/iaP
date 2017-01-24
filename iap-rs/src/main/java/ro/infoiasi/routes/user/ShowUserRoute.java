package ro.infoiasi.routes.user;

import spark.Request;
import spark.Response;
import spark.Route;

public class ShowUserRoute implements Route{
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String name = request.params(":name");
        return null;
    }
}
