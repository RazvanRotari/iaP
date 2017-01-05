package ro.infoiasi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

public class CategoriesResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(CategoriesResponseHandler.class);

    public List<String> get(Request request, Response response) {
        logger.info("Getting categories...");
        return getMockCategories();
    }

    private List<String> getMockCategories() {
        return Arrays.asList("Category1", "Category2", "Category3");
    }
}
