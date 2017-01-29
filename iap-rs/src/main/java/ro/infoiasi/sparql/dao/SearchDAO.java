package ro.infoiasi.sparql.dao;

import org.apache.jena.query.QuerySolution;

import ro.infoiasi.dao.entity.Search;
import static ro.infoiasi.dao.entity.metamodel.SearchMetaModel.*;

import java.util.ArrayList;
import java.util.List;

public class SearchDAO extends GenericDAO<Search>{

	public List<Search> queries = new ArrayList<Search>();
	
	public SearchDAO() {
		super(Search.class);
	}

	@Override
	protected Search toEntity(QuerySolution solution) throws Exception {
		Search s = new Search();
		s.setField(solution.getLiteral(FIELD_VALUE).getString());
		s.setType(solution.getLiteral(TYPE_VALUE).getString());
		s.setValue(solution.getLiteral(VALUE_VALUE).getString());
		return s;
	}
	
	public List<Search> getAllQueries() {
		return queries;
	}
}
