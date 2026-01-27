package Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PromotionProductDAO extends BaseDAO {

    /**
     * Lấy danh sách product_id thuộc promotion
     */
    public List<Integer> getProductIdsByPromotionId(int promotionId) {

        List<Integer> productIds = new ArrayList<>();

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
                productIds.add(rs.getInt("product_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return productIds;
    }
}
