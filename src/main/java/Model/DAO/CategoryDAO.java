package Model.DAO;

import Model.DAO.DBConnection;
import Model.Object.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class CategoryDAO {

    public Map<Integer, Category> getCategoryMap() {
        Map<Integer, Category> map = new HashMap<>();

        String sql = "SELECT id, category_name FROM categories";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Category c = new Category(
                        rs.getInt("id"),
                        rs.getString("category_name")
                );
                map.put(c.getId(), c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
