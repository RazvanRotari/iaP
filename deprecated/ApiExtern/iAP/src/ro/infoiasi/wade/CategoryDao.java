package ro.infoiasi.wade;

import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
	
	public List<Category> categoryList = new ArrayList<Category>();
	
	public CategoryDao() {
		Category cat = new Category("Kittens");
		Category cat2 = new Category("Sports");
		categoryList.add(cat);
		categoryList.add(cat2);
	}

	public List<Category> getAllCategories() {
		return categoryList;
	}

	public Category getCategory(String name) {
		for (Category c : categoryList) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}

	public int deleteCategory(String name) {
		List<Category> catList = getAllCategories();

		for (Category c : catList) {
			if (c.getName().equals(name)) {
				int index = catList.indexOf(c);
				catList.remove(index);
				return 1;
			}
		}
		return 0;
	}

}
