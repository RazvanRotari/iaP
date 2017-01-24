package ro.infoiasi.dao;

import org.apache.commons.lang3.tuple.Triple;
import org.apache.jena.query.QuerySolution;
import ro.infoiasi.dao.entity.User;

import java.util.List;
import java.util.Map;

public class UserDao extends GenericDAO<User> {

    public UserDao(Class<User> clazz) {
        super(clazz);
    }

    @Override
    protected User toEntity(QuerySolution solution) {
        User user = new User();
        user.setPassword(solution.getLiteral("hashValue").getString());
        user.setEmail(solution.getLiteral("emailValue").getString());
        user.setId(solution.getLiteral("idValue").getLong());
        user.setName(solution.getLiteral("nameValue").getString());
        user.setUserName(solution.getLiteral("usernameValue").getString());
        return user;
    }

}
