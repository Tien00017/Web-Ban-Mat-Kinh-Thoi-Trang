package Model.Service;

import Model.DAO.CategoryDAO;
import Model.Object.Category;

import java.util.List;

public class CategoryService {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    public void createCategory(String name) {
        String n = (name == null) ? "" : name.trim();

        if (n.isEmpty()) {
            throw new IllegalArgumentException("empty");
        }
        if (categoryDAO.existsByName(n)) {
            throw new IllegalArgumentException("exists");
        }

        boolean ok = categoryDAO.insert(n);
        if (!ok) {
            throw new RuntimeException("insert_fail");
        }
    }

    public void updateCategory(int id, String name) {
        String n = (name == null) ? "" : name.trim();

        if (n.isEmpty()) {
            throw new IllegalArgumentException("empty");
        }

        boolean ok = categoryDAO.update(id, n);
        if (!ok) {
            throw new RuntimeException("update_fail");
        }
    }

    public void deleteCategory(int id) {
        if (categoryDAO.hasProducts(id)) {
            throw new IllegalStateException("has_products");
        }

        boolean ok = categoryDAO.delete(id);
        if (!ok) {
            throw new RuntimeException("delete_fail");
        }
    }
}
