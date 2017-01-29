package ro.infoiasi.routes.author;

import ro.infoiasi.dao.entity.Author;
import ro.infoiasi.dao.entity.metamodel.AuthorMetaModel;
import ro.infoiasi.sparql.dao.AuthorDAO;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetAuthorDetailsRoute implements Route {
	private AuthorDAO authorDAO = new AuthorDAO();

	@Override
	public Object handle(Request request, Response response) throws Exception {
		String name = request.params(":name");
		Author author = authorDAO
				.find(new SingleFilter(new Equals(Author.class, AuthorMetaModel.NAME_VALUE, Transformer.STR), name));
		return author;
	}
}
