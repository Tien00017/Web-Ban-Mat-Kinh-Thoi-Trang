package Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Map<Integer, Double> getDiscountMapByProductIds(List<Integer> productIds) {
        Map<Integer, Double> map = new HashMap<>();
        if (productIds == null || productIds.isEmpty()) return map;

        String sql = """
        SELECT 
            pp.product_id,
            p.discount_value
        FROM promotion_product pp
        JOIN promotions p ON pp.promotion_id = p.id
        WHERE p.status = 'ACTIVE'
          AND pp.product_id IN (
    """ + productIds.stream().map(id -> "?").collect(Collectors.joining(",")) + ")";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < productIds.size(); i++) {
                ps.setInt(i + 1, productIds.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(
                        rs.getInt("product_id"),
                        rs.getDouble("discount_value")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }


}
