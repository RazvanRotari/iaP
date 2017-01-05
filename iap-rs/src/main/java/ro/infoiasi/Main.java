package ro.infoiasi;

import ro.infoiasi.categories.CategoriesResponseHandler;
import ro.infoiasi.sparql.DBpedia;

import static spark.Spark.*;

public class Main {

    public static final JsonTransformer TRANSFORMER = new JsonTransformer();
    public static final String APPLICATION_JSON = "application/json";

    public static void main(String[] args) {
        port(8080);
        get("/", "text/plain", (request, response) -> "Hello all");
        get("/categories", APPLICATION_JSON, new CategoriesResponseHandler()::get, TRANSFORMER);
        get("/info/:term", APPLICATION_JSON, new DBpedia()::get, TRANSFORMER);
    }

}