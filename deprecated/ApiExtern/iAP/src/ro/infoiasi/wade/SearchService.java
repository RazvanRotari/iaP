package ro.infoiasi.wade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/api/v1/search")
public class SearchService {
	
	public SearchDao queries = new SearchDao();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response search(InputStream incomingData) {
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
		List<Search> queries;
		MediaList response;
		try {
			queries = mapper.readValue(stringBuilder.toString(), mapper.getTypeFactory().constructCollectionType(List.class, Search.class));
			for(Search s:queries) {
				if(!s.getType().toUpperCase().matches("EQ|GR|LS")) {
					ApiError error = new ApiError(400, "Type should be EQ, GR or LS!");
					return Response.status(400).entity(error).build();
				}
			}
			response = new MediaList(queries);
			response.search();
			
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
		return Response.status(200).entity(response.toString()).build();
	}
}
