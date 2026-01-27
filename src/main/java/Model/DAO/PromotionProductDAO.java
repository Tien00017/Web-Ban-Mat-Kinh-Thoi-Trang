package Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PromotionProductDAO extends BaseDAO {

    // Lấy danh sách product_id thuộc 1 promotion
    public List<Integer> getProductIdsByPromotion(int promotionId) {
        List<Integer> list = new ArrayList<>();

        String sql = """
            SELECT product_id
            FROM promotion_product
            WHERE promotion_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, promotionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("product_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
