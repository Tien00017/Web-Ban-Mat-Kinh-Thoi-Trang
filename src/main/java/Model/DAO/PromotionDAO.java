package Model.DAO;

import Model.Object.Promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PromotionDAO extends BaseDAO {

    // Lấy promotion theo id
    public Promotion getById(int id) {

        String sql = """
            SELECT *
            FROM promotions
            WHERE id = ? AND status = 'ACTIVE'
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Promotion p = new Promotion();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setContent(rs.getString("content"));
                p.setStartDate(rs.getDate("start_date"));
                p.setEndDate(rs.getDate("end_date"));
                p.setDiscountType(rs.getString("discount_type"));
                p.setDiscountValue(rs.getDouble("discount_value"));
                p.setStatus(rs.getString("status"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
