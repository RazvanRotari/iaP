package ro.infoiasi.routes.author;

import static spark.Spark.halt;

import org.apache.http.HttpStatus;

import ro.infoiasi.dao.entity.Author;
import ro.infoiasi.dao.entity.metamodel.AuthorMetaModel;
import ro.infoiasi.model.author.AuthorModel;
import ro.infoiasi.routes.RequestBodyParser;
import ro.infoiasi.sparql.dao.AuthorDAO;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;
import spark.Request;
import spark.Response;
import spark.Route;

public class UpdateAuthorRoute implements Route {
	private AuthorDAO authorDAO = new AuthorDAO();

	@Override
	public Object handle(Request request, Response response) throws Exception {
		AuthorModel aModel = new RequestBodyParser().parse(request.body(), AuthorModel.class);
		if (aModel == null) {
			halt(HttpStatus.SC_BAD_REQUEST, "Invalid body");
			return null;
		}
		String id = request.params(":id");
		Author author = authorDAO
				.find(new SingleFilter(new Equals(Author.class, AuthorMetaModel.ID_VALUE, Transformer.STR), id));

		return null;
	}
}
