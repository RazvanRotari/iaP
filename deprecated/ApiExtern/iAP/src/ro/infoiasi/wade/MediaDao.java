package ro.infoiasi.wade;

import java.util.ArrayList;
import java.util.List;

public class MediaDao {
	
	public List<Media> mediaList = new ArrayList<Media>();
	
	public MediaDao() {
		Media media1 = new Media(1, new Category("Economics"),"Stockmarket going up", "Stockmarket is going up for the first time in a couple of years now","http://img1","English","1-02-2017","http://news.xyz",2,3);
		Media media2 = new Media(2, new Category("Hollywood"),"No more Brangelina", "Angelina Joulie and Brad Pit are breaking up!!","http://img1","English","1-02-2017","http://hollywood.xyz",3,2);
		mediaList.add(media1);
		mediaList.add(media2);
	}
	
	public List<Media> getAllMedia() {
		return mediaList;
	}
	
	public Media getMedia(int i) {
		for(Media n : mediaList) {
			if (n.getId() == i) {
				return n;
			}
		}
		return null;
	}
	
	public int updateMedia(Media newMedia) {
		List<Media> mediaList = getAllMedia();
		for(Media n : mediaList) {
			if(n.getTitle().toLowerCase().equals(newMedia.getTitle().toLowerCase())) {
				int index = mediaList.indexOf(n);
				mediaList.set(index, newMedia);
				return 1;
			}
		}
		return 0;
	}
	
	public int deleteMedia(int id) {
		List<Media> mediaList = getAllMedia();
		for(Media m : mediaList) {
			if(Integer.valueOf(m.getId()).equals(id)) {
				int index = mediaList.indexOf(m);
				mediaList.remove(index);
				return 1;
			}
		}
		return 0;
	}
}
