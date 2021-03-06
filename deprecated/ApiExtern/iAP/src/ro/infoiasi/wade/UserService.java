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

@Path("/api/v1/users")
public class UserService {

	static UserDao userDao = new UserDao();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return userDao.getAllUsers();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(InputStream incomingData) {
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
		User user;
		try {
			user = mapper.readValue(stringBuilder.toString(), User.class);
			if (!Integer.valueOf(user.getId()).equals(0)) {
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
		user.setId(userDao.userList.size() + 1);
		if (user.getUsername() == null) {
			return Response.status(400).entity("Please provide username!!").build();
		}
		if (user.getPassword() == null) {
			return Response.status(400).entity("Please provide password!!").build();
		}

		if (!isUsernameUnique(user.getUsername())) {
			ApiError error = new ApiError(409, "Account already exists");
			return Response.status(409).entity(error).build();
		}
		userDao.userList.add(user);
		return Response.status(201).entity(user).build();
	}

	private boolean isUsernameUnique(String username) {
		for (User u : userDao.userList) {
			if (u.getUsername().equals(username))
				return false;
		}
		return true;
	}

	@Path("{username}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("username") String username) {
		boolean found = false;
		for (User u : userDao.userList) {
			if (u.getUsername().toLowerCase().equals(username.toLowerCase())) {
				found = true;
				return Response.status(Response.Status.OK).entity(u).build();
			}
		}
		if (found == false) {
			ApiError error = new ApiError(404, "User " + username + " not found");
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		} else {
			ApiError error = new ApiError(500, "Internal Server Error");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}

	}
	
	@PUT
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("username") String username, InputStream incomingData) {
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
			User updateUser = mapper.readValue(stringBuilder.toString(), User.class);
			for (User u : userDao.userList) {
				if (u.getUsername().equals(username)) {
					if (updateUser.getName() != null)
						u.setName(updateUser.getName());
					if (updateUser.getPassword() != null)
						u.setPassword(updateUser.getPassword());
					if (updateUser.getEmail() != null)
						u.setEmail(updateUser.getEmail());
					return Response.status(Response.Status.OK).entity(u).build();
				}
	
			}
			ApiError error = new ApiError(404, "Username " + username + " not found!");
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ApiError error = new ApiError(400, "Bad argument!");
		return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
	}

	@DELETE
	@Path("{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("username") String username) {
		int result = userDao.deleteUser(username);
		if (result == 1) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		ApiError error = new ApiError(404, "Username " + username + " not found!");
		return Response.status(Response.Status.NOT_FOUND).entity(error).build();
	}

}
