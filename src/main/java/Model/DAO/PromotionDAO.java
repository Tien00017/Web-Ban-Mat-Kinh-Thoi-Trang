package Model.DAO;

import Model.Object.Promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO extends BaseDAO {

    // Lấy các chương trình khuyến mãi mới nhất (hiển thị trang chủ)
    public List<Promotion> getLatestPromotions(int limit) {
        List<Promotion> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM promotions
            WHERE status = 'ACTIVE'
            ORDER BY start_date DESC
            LIMIT ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapPromotion(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy chi tiết 1 khuyến mãi theo ID
    public Promotion getById(int id) {
        String sql = "SELECT * FROM promotions WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapPromotion(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
