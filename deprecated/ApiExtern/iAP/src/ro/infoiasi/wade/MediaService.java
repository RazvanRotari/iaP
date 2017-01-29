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

@Path("/api/v1/media")
public class MediaService {

	static MediaDao mediaDao = new MediaDao();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Media> getMedia() {
		return mediaDao.getAllMedia();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createMedia(InputStream incomingData){
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while((line = in.readLine())!=null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + stringBuilder.toString());
		Media media;
		try {
			media = mapper.readValue(stringBuilder.toString(), Media.class);
			if (!Integer.valueOf(media.getId()).equals(0)) {
				ApiError error = new ApiError(400, "Bad argument");
				return Response.status(400).entity(error).build();
			}
		} catch (JsonParseException e) {
			ApiError error = new ApiError(400, "Bad argument");
			return Response.status(400).entity(error).build();
		} catch (JsonMappingException e) {
			ApiError error = new ApiError(400, "Bad mapping");
			return Response.status(400).entity(error).build();
		} catch (IOException e) {
			ApiError error = new ApiError(400, "IOException");
			return Response.status(400).entity(error).build();
		}
		media.setId(mediaDao.mediaList.size() + 1);
		if (media.getCateg() == null) {
			return Response.status(400).entity("Please provide category!!").build();
		}
		if (media.getTitle() == null) {
			return Response.status(400).entity("Please provide title!!").build();
		}
		if (media.getDescription() == null) {
			return Response.status(400).entity("Please provide description!!").build();
		}
		if (media.getLanguage() == null) {
			return Response.status(400).entity("Please provide language!!").build();
		}
		if (media.getDate() == null) {
			return Response.status(400).entity("Please provide date!!").build();
		}
		if (media.getExternalURL() == null) {
			return Response.status(400).entity("Please provide externalURL!!").build();
		}
		
		if (!isMediaTitleUnique(media.getTitle())) {
			ApiError error = new ApiError(409, "Media already exists");
			return Response.status(409).entity(error).build();
		}
		mediaDao.mediaList.add(media);
		return Response.status(201).entity(media).build();
	}
	
	private boolean isMediaTitleUnique(String title) {
		for(Media m : mediaDao.mediaList) {
			if(m.getTitle().equals(title)) {
				return false;
			}
		}
		return true;
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMedia(@PathParam("id") int id) {
		boolean found = false;
		for (Media m : mediaDao.mediaList) {
			if (Integer.valueOf(m.getId()).equals(id)) {
				found = true;
				return Response.status(Response.Status.OK).entity(m).build();
			}
		}
		if (found == false) {
			ApiError error = new ApiError(404, "Media " + id + " not found");
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		} else {
			ApiError error = new ApiError(500, "Internal Server Error");
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}
	}
	
	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMedia(@PathParam("id") int id, InputStream incomingData) {
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
			Media updateMedia = mapper.readValue(stringBuilder.toString(), Media.class);
			if(!Integer.valueOf(updateMedia.getId()).equals(0)){
				ApiError error = new ApiError(400, "Bad argument!");
				return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
			}
			for (Media m : mediaDao.mediaList) {
				if (Integer.valueOf(m.getId()).equals(id)) {
					if (updateMedia.getTitle() != null)
						m.setTitle(updateMedia.getTitle());
					if (updateMedia.getDescription() != null)
						m.setDescription(updateMedia.getDescription());
					if (updateMedia.getLanguage() != null)
						m.setLanguage(updateMedia.getLanguage());
					if (updateMedia.getDate() != null)
						m.setLanguage(updateMedia.getDate());
					if (updateMedia.getExternalURL() != null)
						m.setLanguage(updateMedia.getExternalURL());
					return Response.status(Response.Status.OK).entity(m).build();
				}
	
			}
			ApiError error = new ApiError(404, "Id " + id + " not found!");
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ApiError error = new ApiError(400, "Bad argument!");
		return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMedia(@PathParam("id") int id) {
		int result = mediaDao.deleteMedia(id);
		if (result == 1) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		ApiError error = new ApiError(404, "Id " + id + " not found!");
		return Response.status(Response.Status.NOT_FOUND).entity(error).build();
	}
}
