package Model.DAO;

import Model.Object.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();

        String sql = """
        SELECT id, category_name
        FROM categories
        ORDER BY id DESC
    """;

        try (Connection con = DBConnection.getConnection()) {

            // ====== DEBUG: xác minh bạn đang connect DB nào ======
            System.out.println("[CategoryDAO] catalog = " + con.getCatalog());
            System.out.println("[CategoryDAO] url = " + con.getMetaData().getURL());
            // =====================================================

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Category c = new Category();
                    c.setId(rs.getInt("id"));
                    c.setCategoryName(rs.getString("category_name"));
                    list.add(c);
                }
            }

            System.out.println("[CategoryDAO] result size = " + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insert(String name) {
        String sql = "INSERT INTO categories(category_name) VALUES (?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(int id, String name) {
        String sql = "UPDATE categories SET category_name=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM categories WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByName(String name) {
        String sql = "SELECT 1 FROM categories WHERE category_name=? LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasProducts(int categoryId) {
        String sql = "SELECT 1 FROM products WHERE category_id=? AND deleted=false LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
