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

@Path("/news")
public class NewsService {

	static NewsDao newsDao = new NewsDao();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getNews() {
		return newsDao.getAllNews();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createNews(InputStream incomingData){
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
		News news;
		try {
			news = mapper.readValue(stringBuilder.toString(), News.class);
			if (!Integer.valueOf(news.getId()).equals(0)) {
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
		news.setId(newsDao.newsList.size() + 1);
		if (news.getCateg() == null) {
			return Response.status(400).entity("Please provide category!!").build();
		}
		if (news.getTitle() == null) {
			return Response.status(400).entity("Please provide title!!").build();
		}
		if (news.getDescription() == null) {
			return Response.status(400).entity("Please provide description!!").build();
		}
		if (news.getLanguage() == null) {
			return Response.status(400).entity("Please provide language!!").build();
		}
		if (news.getDate() == null) {
			return Response.status(400).entity("Please provide date!!").build();
		}
		if (news.getExternalURL() == null) {
			return Response.status(400).entity("Please provide externalURL!!").build();
		}
		
		if (!isNewsTitleUnique(news.getTitle())) {
			ApiError error = new ApiError(409, "News already exists");
			return Response.status(409).entity(error).build();
		}
		newsDao.newsList.add(news);
		return Response.status(201).entity(news).build();
	}
	
	private boolean isNewsTitleUnique(String title) {
		for(News n : newsDao.newsList) {
			if(n.getTitle().equals(title)) {
				return false;
			}
		}
		return true;
	}
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@PathParam("id") int id) {
		boolean found = false;
		for (News n : newsDao.newsList) {
			if (Integer.valueOf(n.getId()).equals(id)) {
				found = true;
				return Response.status(Response.Status.OK).entity(n).build();
			}
		}
		if (found == false) {
			ApiError error = new ApiError(404, "News " + id + " not found");
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
	public Response updateNews(@PathParam("id") int id, InputStream incomingData) {
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
			News updateNews = mapper.readValue(stringBuilder.toString(), News.class);
			for (News n : newsDao.newsList) {
				if (Integer.valueOf(n.getId()).equals(id)) {
					if (updateNews.getTitle() != null)
						n.setTitle(updateNews.getTitle());
					if (updateNews.getDescription() != null)
						n.setDescription(updateNews.getDescription());
					if (updateNews.getLanguage() != null)
						n.setLanguage(updateNews.getLanguage());
					if (updateNews.getDate() != null)
						n.setLanguage(updateNews.getDate());
					if (updateNews.getExternalURL() != null)
						n.setLanguage(updateNews.getExternalURL());
					return Response.status(Response.Status.OK).entity(n).build();
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
	public Response deleteNews(@PathParam("id") int id) {
		int result = newsDao.deleteNews(id);
		if (result == 1) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		ApiError error = new ApiError(404, "Id " + id + " not found!");
		return Response.status(Response.Status.NOT_FOUND).entity(error).build();
	}
}
