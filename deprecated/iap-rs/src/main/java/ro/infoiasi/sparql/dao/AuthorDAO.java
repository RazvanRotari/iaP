package ro.infoiasi.sparql.dao;

import static ro.infoiasi.dao.entity.metamodel.AuthorMetaModel.*;
import static ro.infoiasi.dao.entity.metamodel.UserMetaModel.ID_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;

import ro.infoiasi.dao.entity.Author;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregatePropertyFunction;
import ro.infoiasi.sparql.insertionPoints.subqueries.AggregateSubQuery;

public class AuthorDAO extends GenericDAO<Author>{

	public List<Author> authorList = new ArrayList<Author>();
	
	public AuthorDAO() {
		super(Author.class);
	}

	@Override
	protected Author toEntity(QuerySolution solution) throws Exception {
		Author a = new Author();
        a.setName(solution.getLiteral(NAME_VALUE).getString());
        a.setRating(solution.getLiteral(RATING_VALUE).getLong());
        return a;
	}
	public List<Author> getAllAuthors() {
		return authorList;
	}
	
	public Author getAuthor(int i) {
		for(Author a:authorList) {
			if(a.getId() == i) {
				return a;
			}
		}
		return null;
	}
	
	public int updateAuthor(Author updateAuthor) {
		List<Author> authorList = getAllAuthors();
		for(Author a:authorList) {
			if(a.getName().toLowerCase().equals(updateAuthor.getName().toLowerCase())) {
				int index = authorList.indexOf(a);
				authorList.set(index, updateAuthor);
				return 1;
			}
		}
		return 0;
	}
	
	public int deleteAuthor(String name) {
		List<Author> authorList = getAllAuthors();

		for (Author a : authorList) {
			if (a.getName().equals(name)) {
				int index = authorList.indexOf(a);
				authorList.remove(index);
				return 1;
			}
		}
		return 0;
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

}
