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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/api/v1/categories")
public class CategoryService {

	static CategoryDao catDao = new CategoryDao();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories() {
		return catDao.getAllCategories();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCategory(InputStream incomingData) {
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
		Category cat;
		try {
			cat = mapper.readValue(stringBuilder.toString(), Category.class);
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
		if (cat.getName() == null) {
			return Response.status(400).entity("Please provide name!!").build();
		}

		if (!isCategoryNameUnique(cat.getName())) {
			ApiError error = new ApiError(409, "Category already exists");
			return Response.status(409).entity(error).build();
		}
		catDao.categoryList.add(cat);
		return Response.status(201).entity(cat).build();
	}

	private boolean isCategoryNameUnique(String name) {
		for (Category c : catDao.categoryList) {
			if (c.getName().toLowerCase().equals(name.toLowerCase()))
				return false;
		}
		return true;
	}

	@DELETE
	@Path("{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCategory(@PathParam("name") String name) {
		int result = catDao.deleteCategory(name);
		if (result == 1) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		ApiError error = new ApiError(404, "Category " + name + " not found!");
		return Response.status(Response.Status.NOT_FOUND).entity(error).build();
	}

}
