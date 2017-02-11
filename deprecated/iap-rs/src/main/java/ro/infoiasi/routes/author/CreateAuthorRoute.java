package ro.infoiasi.routes.author;

import static spark.Spark.halt;

import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpStatus;

import ro.infoiasi.dao.entity.Author;
import ro.infoiasi.model.author.AuthorModel;
import ro.infoiasi.routes.RequestBodyParser;
import ro.infoiasi.sparql.dao.AuthorDAO;
import spark.Request;
import spark.Response;
import spark.Route;

public class CreateAuthorRoute implements Route {

	private AuthorDAO authorDAO = new AuthorDAO();

	@Override
	public Object handle(Request request, Response response) throws Exception {
		try {
			AuthorModel aModel = new RequestBodyParser().parse(request.body(), AuthorModel.class);
			if (aModel == null) {
				halt(HttpStatus.SC_BAD_REQUEST, "Invalid body");
				return null;
			}
			Author author = toAuthor(aModel);
			if (author == null) {
				halt(HttpStatus.SC_NOT_IMPLEMENTED, "Problems with creating the author");
				return null;
			}
			if (author.getName() == null) {
				halt(HttpStatus.SC_BAD_REQUEST, "Please provide name!!");
				return null;
			}
			if (author.getRating()==0) {
				halt(HttpStatus.SC_BAD_REQUEST, "Please provide rating!!");
				return null;
			}
			if (!isNameUnique(author.getName())) {
				halt(HttpStatus.SC_CONFLICT, "Author already exists");
				return null;
			}
			authorDAO.create(author);
		} catch (Exception ex) {
		}
		return null;
	}

	private boolean isNameUnique(String name) {
		for (Author a : authorDAO.authorList) {
			if (a.getName().equals(name))
				return false;
		}
		return true;
	}
	
	private Author toAuthor(AuthorModel authorModel) throws Exception {
        try {
        	if(authorModel.getId()!=0) {
        		halt(HttpStatus.SC_BAD_REQUEST, "Id should not be in the request body!");
        		return null;
        	}
            Author author = new Author();
            author.setName(authorModel.getName());
            author.setRating(authorModel.getRating());
            author.setId(authorDAO.getNextId());
            return author;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    
}
