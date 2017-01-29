package ro.infoiasi.wade;

import java.util.ArrayList;
import java.util.List;

public class SearchDao {

	public List<Search> queries = new ArrayList<Search>();

	public SearchDao() {
		Search s1 = new Search("field1", "EQ", "val1");
		Search s2 = new Search("field2", "GR", "val2");
		Search s3 = new Search("field3", "LS", "val3");
		queries.add(s1);
		queries.add(s2);
		queries.add(s3);
	}
	
	public List<Search> getAllQueries() {
		return queries;
	}

}
