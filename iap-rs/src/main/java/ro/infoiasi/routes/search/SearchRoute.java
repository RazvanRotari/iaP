package ro.infoiasi.routes.search;

import ro.infoiasi.views.SearchResult;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchRoute implements Route {

    @Override
    public SearchResult handle(Request request, Response response) throws Exception {
        String query = request.attribute("q");
        return new SearchResult();
    }
}
