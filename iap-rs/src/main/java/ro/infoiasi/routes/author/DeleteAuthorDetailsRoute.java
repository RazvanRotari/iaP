package ro.infoiasi.routes.author;

import static spark.Spark.halt;

import org.apache.http.HttpStatus;

import ro.infoiasi.dao.entity.Author;
import ro.infoiasi.dao.entity.User;
import ro.infoiasi.dao.entity.metamodel.AuthorMetaModel;
import ro.infoiasi.dao.entity.metamodel.UserMetaModel;
import ro.infoiasi.sparql.dao.AuthorDAO;
import ro.infoiasi.sparql.insertionPoints.filter.SingleFilter;
import ro.infoiasi.sparql.insertionPoints.predicate.Equals;
import ro.infoiasi.sparql.insertionPoints.transformer.Transformer;
import spark.Request;
import spark.Response;
import spark.Route;

public class DeleteAuthorDetailsRoute implements Route {
	private AuthorDAO authorDAO;

	public DeleteAuthorDetailsRoute() {
        authorDAO = new AuthorDAO();
    }

	@Override
	public Object handle(Request request, Response response) throws Exception {
		String name = request.params(":name");
		Equals stringEqualsPredicate = new Equals(Author.class, AuthorMetaModel.NAME, Transformer.STR);
		Author author = authorDAO.find(new SingleFilter(stringEqualsPredicate, name));
		if (author.getName().equals(name)) {
			authorDAO.delete(author);
			return 204;
		} else {
			halt(HttpStatus.SC_NOT_FOUND, "Author " + author + " not found");
			return 404;
		}
	}
}
