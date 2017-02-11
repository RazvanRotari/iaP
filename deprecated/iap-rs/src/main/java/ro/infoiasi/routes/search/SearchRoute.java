package ro.infoiasi.routes.search;

import com.google.gson.reflect.TypeToken;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.engine.SearchEngine;
import ro.infoiasi.model.search.SearchQuery;
import ro.infoiasi.routes.RequestBodyParser;
import ro.infoiasi.views.SearchResult;
import spark.Request;
import spark.Response;
import spark.Route;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.halt;

public class SearchRoute implements Route {

    @Override
    public List<SearchResult> handle(Request request, Response response) throws Exception {
        List<SearchQuery> query = getSearchQuery(request);
        if(query == null || query.isEmpty()) {
            halt(400, "Invalid request");
        }
        SearchEngine engine = new SearchEngine();
        return null;
    }

    private List<SearchQuery> getSearchQuery(Request request) {
        String requestBody = request.body();
        Type listType = new TypeToken<ArrayList<SearchQuery>>(){}.getType();
        return new RequestBodyParser().parse(requestBody, listType);
    }
}
