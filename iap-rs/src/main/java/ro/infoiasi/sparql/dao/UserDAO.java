package ro.infoiasi.sparql.dao;

import static ro.infoiasi.dao.entity.metamodel.UserMetaModel.EMAIL_VALUE;
import static ro.infoiasi.dao.entity.metamodel.UserMetaModel.HASH_VALUE;
import static ro.infoiasi.dao.entity.metamodel.UserMetaModel.ID_VALUE;
import static ro.infoiasi.dao.entity.metamodel.UserMetaModel.NAME_VALUE;
import static ro.infoiasi.dao.entity.metamodel.UserMetaModel.USERNAME_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;

import ro.infoiasi.dao.entity.User;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregatePropertyFunction;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregateSubQuery;

public class UserDAO extends GenericDAO<User> {
	
	public List<User> userList = new ArrayList<User>();
	
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

    public long getNextId() throws Exception {
        String mapping = "next";
        String query = new AggregateSubQuery(clazz, AggregatePropertyFunction.MAX, ID_VALUE, mapping).construct();
        if (DEBUG) {
            logger.debug(query);
        }
        ResultSet resultSet = QueryExecutionFactory.sparqlService(HTTP_ENDPOINT, query).execSelect();
        if (resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            return getCurrentValue(mapping, solution) + 1;
        }
        throw new Exception("Could not query for next id");
    }

    private long getCurrentValue(String mapping, QuerySolution solution) {
        Literal literal = solution.getLiteral(mapping);
        if(literal == null) {
            return 0;
        }
        return literal.getLong();
    }
    
    public List<User> getAllUsers() {
		return userList;
	}
    
    public User getUser(int i) {
		for (User u : userList) {
			if (u.getId() == i) {
				return u;
			}
		}
		return null;
	}

    public int updateUser(User pUser) {
		List<User> userList = getAllUsers();
		for (User user : userList) {
			if (user.getUserName().equals(pUser.getUserName())) {
				int index = userList.indexOf(user);
				userList.set(index, pUser);
				return 1;
			}
		}
		return 0;
	}

	public int deleteUser(String username) {
		List<User> userList = getAllUsers();

		for (User user : userList) {
			if (user.getUserName().equals(username)) {
				int index = userList.indexOf(user);
				userList.remove(index);
				return 1;
			}
		}
		return 0;
	}

}
