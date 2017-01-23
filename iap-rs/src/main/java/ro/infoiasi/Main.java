package ro.infoiasi;

import ro.infoiasi.categories.CategoriesResponseHandler;
import ro.infoiasi.sparql.DBpedia;
import ro.infoiasi.user.User;
import ro.infoiasi.user.UserDao;

import static spark.Spark.*;

public class Main {

    public static final JsonTransformer TRANSFORMER = new JsonTransformer();
    public static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) throws Exception {
        /*
        port(8080);
        get("/", "text/plain", (request, response) -> "Hello all");
        get("/categories", APPLICATION_JSON, new CategoriesResponseHandler()::get, TRANSFORMER);
        get("/info/:term", APPLICATION_JSON, new DBpedia()::get, TRANSFORMER);
        */
        User user = new User();
        user.setId(1);
        user.setName("John Doe");
        user.setPassword("blyat");
        user.setUserName("johnDoe");

        UserDao userDao = new UserDao();
        userDao.create(user);
    }

}