package ro.infoiasi;

import ro.infoiasi.routes.categories.CategoriesResponseHandler;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.dao.UserDao;
import ro.infoiasi.routes.search.SearchRoute;
import ro.infoiasi.routes.user.AllUsersRoute;
import ro.infoiasi.routes.user.DeleteUserRoute;
import ro.infoiasi.routes.user.ShowUserRoute;
import ro.infoiasi.routes.user.UpdateUserRoute;
import ro.infoiasi.util.JsonTransformer;
import spark.Route;

import static spark.Spark.*;

public class Main {

    public static final JsonTransformer TRANSFORMER = new JsonTransformer();
    public static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) throws Exception {

        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setPassword("password");
        user.setUserName("johnDoe");
        user.setEmail("jondoe@test.com");

        UserDao userDao = new UserDao(User.class);
        /*
        user = userDao.find("name", "John Doe");
        System.out.println(user);
        */
        userDao.update(user);
    }

    public static void startServer() {
        port(8080);
        get("/", "text/plain", (request, response) -> "Hello all");
        json_get("/users/:user", new ShowUserRoute());
        json_post("/users", new AllUsersRoute());
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