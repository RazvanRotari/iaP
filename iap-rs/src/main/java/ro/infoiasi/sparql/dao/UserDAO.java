package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.shared.NotFoundException;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregateFunction;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregateSubQuery;

public class UserDAO extends GenericDAO<User> {

    public UserDAO(Class<User> clazz) {
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

    public long getNextId() {
        String mapping = "next";
        String query = new AggregateSubQuery(clazz, AggregateFunction.MAX, "id", mapping).construct();
        if (DEBUG) {
            System.out.println(query);
        }
        ResultSet resultSet = QueryExecutionFactory.sparqlService(HTTP_ENDPOINT, query).execSelect();
        if (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            return solution.get(mapping).asLiteral().getLong() + 1;
        }
        throw new NotFoundException("Could not query for next id");
    }
}