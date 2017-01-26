package ro.infoiasi;

import ro.infoiasi.sparql.dao.UserDAO;
import ro.infoiasi.routes.user.*;
import ro.infoiasi.routes.categories.CategoriesResponseHandler;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.routes.search.SearchRoute;
import ro.infoiasi.sparql.filter.SingleFilter;
import ro.infoiasi.sparql.predicate.Equals;
import ro.infoiasi.sparql.predicate.Transformer;
import ro.infoiasi.util.JsonTransformer;
import spark.Route;

import static spark.Spark.*;

public class Main {

    public static final JsonTransformer TRANSFORMER = new JsonTransformer();
    public static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) throws Exception {
        //startServer();
        playground();
    }

    private static void playground() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setPassword("password");
        user.setUserName("johnDoe");
        user.setEmail("jondoe@test.com");

        UserDAO userDAO = new UserDAO(User.class);
        user = userDAO.find(new SingleFilter(new Equals("nameValue", Transformer.STR), "John Doe"));
        System.out.println(user);

    }

    public static void startServer() {
        port(8080);
        get("/", "text/plain", (request, response) -> "Hello");
        json_get("/users/:user", new ShowUserDetailsRoute());
        json_post("/users/login", new UserLoginRote());
        json_post("/users/logout", new UserLogountRoute());
        json_put("/users/:user", new UpdateUserRoute());
        json_delete("/users/:user", new DeleteUserRoute());

        json_get("/categories", new CategoriesResponseHandler());
        json_get("/search", new SearchRoute());
    }

    private static void json_delete(String path, Route route) {
        delete(path, APPLICATION_JSON, route, TRANSFORMER);
    }

    private static void json_post(String path, Route route) {
        post(path, APPLICATION_JSON, route, TRANSFORMER);
    }

    private static void json_put(String path, Route route) {
        put(path, APPLICATION_JSON, route, TRANSFORMER);
    }

    private static void json_get(String path, Route route) {
        get(path, APPLICATION_JSON, route, TRANSFORMER);
    }

}