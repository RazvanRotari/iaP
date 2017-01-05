package ro.infoiasi;

import static spark.Spark.*;

public class Main {

    public static final JsonTransformer TRANSFORMER = new JsonTransformer();

    public static void main(String[] args) {
        port(8080);
        get("/", "text/plain", (request, response) -> "Hello all");
        get("/categories", "application/json", new CategoriesResponseHandler()::get, TRANSFORMER);
    }

}