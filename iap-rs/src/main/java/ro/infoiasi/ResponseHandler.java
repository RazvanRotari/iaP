package ro.infoiasi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.Arrays;
import java.util.List;

public interface ResponseHandler<T> {
    T get(Request request, Response response);
}
