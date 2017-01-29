package ro.infoiasi;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import ro.infoiasi.routes.author.*;
import ro.infoiasi.routes.categories.CategoriesResponseHandler;
import ro.infoiasi.routes.categories.CategoryCreatorRoute;
import ro.infoiasi.routes.categories.DeleteCategoryRoute;
import ro.infoiasi.routes.media.*;
import ro.infoiasi.routes.search.SearchRoute;
import ro.infoiasi.routes.user.DeleteUserRoute;
import ro.infoiasi.routes.user.ShowUserDetailsRoute;
import ro.infoiasi.routes.user.UpdateUserRoute;
import ro.infoiasi.routes.user.UserLoginRote;
import ro.infoiasi.routes.user.UserLogountRoute;
import ro.infoiasi.util.JsonTransformer;
import spark.Route;

public class Main {

    public static final String BASE_URL = "http://wade.razvanrotari.me";
    public static final JsonTransformer TRANSFORMER = new JsonTransformer();
    public static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) throws Exception {
        startServer();
    }

    public static void startServer() {
        port(8080);
        json_get("/api/v1/users/:user", new ShowUserDetailsRoute());
        json_post("/api/v1/users/login", new UserLoginRote());
        json_post("/api/v1/users/logout", new UserLogountRoute());
        json_put("/api/v1/users/:user", new UpdateUserRoute());
        json_delete("/api/v1/users/:user", new DeleteUserRoute());

        json_get("/api/v1/media", new AllMediaRoute());
        json_post("/api/v1/media", new CreateMediaRoute());

        json_delete("/api/v1/media/:id", new DeleteMediaRoute());
        json_get("/api/v1/media/:id", new GetMediaItemRoute());
        json_put("/api/v1/media/:id", new UpdateMediaRoute());

        json_get("/api/v1/categories", new CategoriesResponseHandler());
        json_post("/api/v1/categories", new CategoryCreatorRoute());
        json_post("/api/v1/categories/:name", new DeleteCategoryRoute());

        json_get("/api/v1/authors", new ListAuthorsRoute());
        json_post("/api/v1/authors", new CreateAuthorRoute());

        json_delete("/api/v1/authors/:name", new DeleteAuthorDetailsRoute());
        json_get("/api/v1/authors/:name", new GetAuthorDetailsRoute());
        json_put("/api/v1/authors/:name", new UpdateAuthorRoute());

        json_post("/api/v1/search", new SearchRoute());
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