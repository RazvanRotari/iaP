package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.shared.NotFoundException;
import ro.infoiasi.dao.entity.User;
import static ro.infoiasi.dao.entity.metamodel.UserMetaModel.*;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregateFunction;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregateSubQuery;

public class UserDAO extends GenericDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    @Override
    protected User toEntity(QuerySolution solution) {
        User user = new User();
        user.setPassword(solution.getLiteral(HASH_VALUE).getString());
        user.setEmail(solution.getLiteral(EMAIL_VALUE).getString());
        user.setId(solution.getLiteral(ID_VALUE).getLong());
        user.setName(solution.getLiteral(NAME_VALUE).getString());
        user.setUserName(solution.getLiteral(USERNAME_VALUE).getString());
        return user;
    }

    public long getNextId() {
        String mapping = "next";
        String query = new AggregateSubQuery(clazz, AggregateFunction.MAX, ID, mapping).construct();
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
