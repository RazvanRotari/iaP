package ro.infoiasi.wade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/authors")
public class AuthorService {
	static AuthorDao authorDao = new AuthorDao();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Author> getAuthors() {
		
		return authorDao.getAllAuthors();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAuthor(InputStream incomingData) {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + stringBuilder.toString());
		Author author;
		try {
			author = mapper.readValue(stringBuilder.toString(), Author.class);
			if (!Integer.valueOf(author.getId()).equals(0)) {
				ApiError error = new ApiError(400, "Bad argument");
				return Response.status(400).entity(error).build();
			}
		} catch (JsonParseException e) {
			ApiError error = new ApiError(400, "Bad argument");
			return Response.status(400).entity(error).build();
		} catch (JsonMappingException e) {
			ApiError error = new ApiError(400, "Missing field");
			return Response.status(400).entity(error).build();
		} catch (IOException e) {
			ApiError error = new ApiError(400, "IOException");
			return Response.status(400).entity(error).build();
		}
		author.setId(authorDao.authorList.size() + 1);
		if (author.getName() == null) {
			return Response.status(400).entity("Please provide name!!").build();
		}
		if (!isNameUnique(author.getName())) {
			ApiError error = new ApiError(409, "Author already exists");
			return Response.status(409).entity(error).build();
		}
		authorDao.authorList.add(author);
		return Response.status(201).entity(author).build();
	}

	private boolean isNameUnique(String name) {
		for (Author a : authorDao.authorList) {
			if (a.getName().toLowerCase().equals(name.toLowerCase()))
				return false;
		}
		return true;
	}

	@Path("{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAuthor(@PathParam("name") String name) {
		boolean found = false;
		for (Author a : authorDao.authorList) {
			if (a.getName().toLowerCase().equals(name.toLowerCase())) {
				found = true;
				return Response.status(Response.Status.OK).entity(a).build();
			}
		}
		if (found == false) {
			ApiError error = new ApiError(404, "Author " + name + " not found");
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		} else {
			ApiError error = new ApiError(500, "Internal Server Error");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}
	}
	
	@PUT
	@Path("{name}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAuthor(@PathParam("name") String name, InputStream incomingData) {
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + stringBuilder.toString());
		 
		try {
			Author updateAuthor = mapper.readValue(stringBuilder.toString(), Author.class);
			for (Author a : authorDao.authorList) {
				if (a.getName().toLowerCase().equals(name.toLowerCase())) {
					if (!Integer.valueOf(updateAuthor.getRating()).equals(0))
						a.setRating(updateAuthor.getRating());
					return Response.status(Response.Status.OK).entity(a).build();
				}
	
			}
			ApiError error = new ApiError(404, "Author " + name + " not found!");
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ApiError error = new ApiError(400, "Bad argument!");
		return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
	}

	@DELETE
	@Path("{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("name") String name) {
		int result = authorDao.deleteAuthor(name);
		if (result == 1) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		ApiError error = new ApiError(404, "Author " + name + " not found!");
		return Response.status(Response.Status.NOT_FOUND).entity(error).build();
	}

}
