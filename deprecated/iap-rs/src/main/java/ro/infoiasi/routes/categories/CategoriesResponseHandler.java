package ro.infoiasi.routes.categories;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Arrays;
import java.util.List;

public class CategoriesResponseHandler implements Route {
    private static final Logger logger = LoggerFactory.getLogger(CategoriesResponseHandler.class);

    private List<String> getMockCategories() {
        return Arrays.asList("Category1", "Category2", "Category3");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        logger.info("Getting categories...");
        return getMockCategories();
    }
}
