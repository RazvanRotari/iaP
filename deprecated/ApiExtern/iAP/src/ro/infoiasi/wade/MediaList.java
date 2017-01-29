package ro.infoiasi.wade;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MediaList {
	
	@JsonProperty(value = "responses")
	private List<Media> responses = new ArrayList<Media>();
	private MediaDao mediaDao = new MediaDao();
	private List<Search> queries = new ArrayList<Search>();
	
	public MediaList(List<Search> queries) {
		this.queries=queries;
	}
	
	public void search() {
		List<Media> allMedia = mediaDao.getAllMedia();
		
		for(Search s:queries) {
			if(s.getField().toLowerCase().equals("id")) {
				if(s.getType().toLowerCase().equals("eq")) {
					for(Media m:allMedia) {
						if(m.getId()==Integer.valueOf(s.getValue())) {
							responses.add(m);
						}
					}
				} else if(s.getType().toLowerCase().equals("gr")) {
					for(Media m:allMedia) {
						if(m.getId()>Integer.valueOf(s.getValue())) {
							responses.add(m);
						}
					}
				} else if(s.getType().toLowerCase().equals("ls")) {
					for(Media m:allMedia) {
						if(m.getId()<Integer.valueOf(s.getValue())) {
							responses.add(m);
						}
					}
				}
			}
			if(s.getField().toLowerCase().equals("category")) {
				if(s.getType().toLowerCase().equals("eq")) {
					for(Media m:allMedia) {
						if(m.getCateg().getName().toLowerCase().equals(s.getValue().toLowerCase())) {
							responses.add(m);
						}
					}
				}
			}
			if(s.getField().toLowerCase().equals("title")) {
				if(s.getType().toLowerCase().equals("eq")) {
					for(Media m:allMedia) {
						if(m.getTitle().toLowerCase().equals(s.getValue().toLowerCase())) {
							responses.add(m);
						}
					}
				}
			}
			if(s.getField().toLowerCase().equals("language")) {
				if(s.getType().toLowerCase().equals("eq")) {
					for(Media m:allMedia) {
						if(m.getLanguage().toLowerCase().equals(s.getValue().toLowerCase())) {
							responses.add(m);
						}
					}
				}
			}
			if(s.getField().toLowerCase().equals("description")) {
				if(s.getType().toLowerCase().equals("eq")) {
					for(Media m:allMedia) {
						if(m.getDescription().toLowerCase().equals(s.getValue().toLowerCase())) {
							responses.add(m);
						}
					}
				}
			}
			if(s.getField().toLowerCase().equals("url")) {
				if(s.getType().toLowerCase().equals("eq")) {
					for(Media m:allMedia) {
						if(m.getUrl().toLowerCase().equals(s.getValue().toLowerCase())) {
							responses.add(m);
						}
					}
				}
			}
			if(s.getField().toLowerCase().equals("externalurl")) {
				if(s.getType().toLowerCase().equals("eq")) {
					for(Media m:allMedia) {
						if(m.getExternalURL().toLowerCase().equals(s.getValue().toLowerCase())) {
							responses.add(m);
						}
					}
				}
			}
			if(s.getField().toLowerCase().equals("authorRating")) {
				if(s.getType().toLowerCase().equals("eq")) {
					for(Media m:allMedia) {
						if(m.getAuthorRating()==Integer.valueOf(s.getValue())) {
							responses.add(m);
						}
					}
				} else if(s.getType().toLowerCase().equals("gr")) {
					for(Media m:allMedia) {
						if(m.getAuthorRating()>Integer.valueOf(s.getValue())) {
							responses.add(m);
						}
					}
				} else if(s.getType().toLowerCase().equals("ls")) {
					for(Media m:allMedia) {
						if(m.getAuthorRating()<Integer.valueOf(s.getValue())) {
							responses.add(m);
						}
					}
				}
			}
			if(s.getField().toLowerCase().equals("mediaRating")) {
				if(s.getType().toLowerCase().equals("eq")) {
					for(Media m:allMedia) {
						if(m.getMediaRating()==Integer.valueOf(s.getValue())) {
							responses.add(m);
						}
					}
				} else if(s.getType().toLowerCase().equals("gr")) {
					for(Media m:allMedia) {
						if(m.getMediaRating()>Integer.valueOf(s.getValue())) {
							responses.add(m);
						}
					}
				} else if(s.getType().toLowerCase().equals("ls")) {
					for(Media m:allMedia) {
						if(m.getMediaRating()<Integer.valueOf(s.getValue())) {
							responses.add(m);
						}
					}
				}
			}
		}
	}
	
	public List<Media> getResponses() {
		return responses;
	}
	
	@Override
	public String toString() {
		String s="[";
		for(Media m:responses) {
			s+=m.toString();
			s+=",";
		}
		s=s.substring(0, s.length()-1);
		s+="]";
		return s;
	}
}
