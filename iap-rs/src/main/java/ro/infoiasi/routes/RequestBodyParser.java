package ro.infoiasi.routes;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.infoiasi.routes.user.UserLoginRote;

import java.lang.reflect.Type;

public class RequestBodyParser {
    private Gson gson = new Gson();
    private static final Logger logger = LoggerFactory.getLogger(UserLoginRote.class);

    public<T> T parse(String requestBody, Class<T> clazz) {
        try {
            return gson.fromJson(requestBody, clazz);
        } catch (JsonSyntaxException jse) {
            logger.error("Could not parse credentials", jse);
            return null;
        }
    }

    public<T> T parse(String requestBody, Type type) {
        try {
            return gson.fromJson(requestBody, type);
        } catch (JsonSyntaxException jse) {
            logger.error("Could not parse credentials", jse);
            return null;
        }
    }
}
