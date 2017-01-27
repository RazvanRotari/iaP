package ro.infoiasi.wade;

import java.util.ArrayList;
import java.util.List;

public class NewsDao {
	
	public List<News> newsList = new ArrayList<News>();
	
	public NewsDao() {
		News news1 = new News(1, new Category(3,"Economics"),"Stockmarket going up", "Stockmarket is going up for the first time in a couple of years now","English","1-02-2017","http://news.xyz");
		News news2 = new News(2, new Category(4,"Hollywood"),"No more Brangelina", "Angelina Joulie and Brad Pit are breaking up!!","English","1-02-2017","http://hollywood.xyz");
		newsList.add(news1);
		newsList.add(news2);
	}
	
	public List<News> getAllNews() {
		return newsList;
	}
	
	public News getNews(int i) {
		for(News n : newsList) {
			if (n.getId() == i) {
				return n;
			}
		}
		return null;
	}
	
	public int updateNews(News uNews) {
		List<News> newsList = getAllNews();
		for(News n : newsList) {
			if(n.getTitle().toLowerCase().equals(uNews.getTitle().toLowerCase())) {
				int index = newsList.indexOf(n);
				newsList.set(index, uNews);
				return 1;
			}
		}
		return 0;
	}
	
	public int deleteNews(int id) {
		List<News> newsList = getAllNews();
		for(News n : newsList) {
			if(Integer.valueOf(n.getId()).equals(id)) {
				int index = newsList.indexOf(n);
				newsList.remove(index);
				return 1;
			}
		}
		return 0;
	}
}
