package ro.infoiasi.sparql.dao;

import static ro.infoiasi.dao.entity.metamodel.CategoryMetaModel.NAME_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;

import ro.infoiasi.dao.entity.Category;

public class CategoryDAO extends GenericDAO<Category>{

	public List<Category> categoryList = new ArrayList<Category>();
	
	public CategoryDAO() {
		super(Category.class);
	}

	@Override
	protected Category toEntity(QuerySolution solution) throws Exception {
		Category c = new Category();
        c.setName(solution.getLiteral(NAME_VALUE).getString());
        return c;
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
