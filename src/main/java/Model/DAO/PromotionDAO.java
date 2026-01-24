package Model.DAO;

import Model.Object.Promotion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PromotionDAO {
    public int insert(Promotion p) {
        String sql = """
        INSERT INTO promotions
        (title, content, start_date, end_date,
         discount_type, discount_value, status)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTitle());
            ps.setString(2, p.getContent());
            ps.setDate(3, p.getStartDate());
            ps.setDate(4, p.getEndDate());
            ps.setString(5, p.getDiscountType());
            ps.setDouble(6, p.getDiscountValue());
            ps.setString(7, p.getStatus());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return 0;
    }
    public void insertPromotionProduct(int promotionId, int productId) {
        String sql = """
        INSERT INTO promotion_product (promotion_id, product_id)
        VALUES (?, ?)
    """;

        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, promotionId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        }
    }
    public void insertBanner(int promotionId, String url) {
        String sql = """
        INSERT INTO banners
        (promotions_id, image_url, is_main, created_at)
        VALUES (?, ?, 1, NOW())
    """;
    }
}
