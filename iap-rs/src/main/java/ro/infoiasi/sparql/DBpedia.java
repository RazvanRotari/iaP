package ro.infoiasi.sparql;

import org.apache.jena.query.*;
import ro.infoiasi.ResponseHandler;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class DBpedia implements ResponseHandler<List<String>> {

    final String ENDPOINT = "http://dbpedia.org/sparql";

    private String buildQuery(String term) {
        return "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "SELECT * WHERE {" +
                "    ?o rdfs:label \"" + term + "\"@en." +
                "    ?s ?p ?o" +
                "}";
    }

    @Override
    public List<String> get(Request request, Response response) {
        List<String> results = new ArrayList<>();
        String term = request.params(":term");
        ResultSet resultSet = QueryExecutionFactory.sparqlService( ENDPOINT, buildQuery(term)).execSelect();
        while(resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            results.add(solution.get("s").asResource().getURI());
        }
        return results;
    }
}
