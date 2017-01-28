package ro.infoiasi.wade;

import java.util.ArrayList;
import java.util.List;

public class AuthorDao {

	public List<Author> authorList = new ArrayList<Author>();
	
	public AuthorDao() {
		Author a1 = new Author(1,"New York Times",5);
		Author a2 = new Author(2, "Imgur",1);
		authorList.add(a1);
		authorList.add(a2);
	}
	
	public List<Author> getAllAuthors() {
		return authorList;
	}
	
	public Author getAuthor(int i) {
		for(Author a:authorList) {
			if(a.getId() == i) {
				return a;
			}
		}
		return null;
	}
	
	public int updateAuthor(Author updateAuthor) {
		List<Author> authorList = getAllAuthors();
		for(Author a:authorList) {
			if(a.getName().toLowerCase().equals(updateAuthor.getName().toLowerCase())) {
				int index = authorList.indexOf(a);
				authorList.set(index, updateAuthor);
				return 1;
			}
		}
		return 0;
	}
	
	public int deleteAuthor(String name) {
		List<Author> authorList = getAllAuthors();

		for (Author a : authorList) {
			if (a.getName().equals(name)) {
				int index = authorList.indexOf(a);
				authorList.remove(index);
				return 1;
			}
		}
		return 0;
	}
}
