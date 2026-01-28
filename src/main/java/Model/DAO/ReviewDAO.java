package Model.DAO;

import Model.Object.Review;
import Model.Object.User;
import java.sql.Connection;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import Model.DAO.UserDAO;

public class ReviewDAO extends BaseDAO  {

    // ================= GET ALL REVIEWS =================
    public Map<User, Review> getAllReviewsByProductId(int productId) {

        Map<User, Review> map = new LinkedHashMap<>();

        String sql = """
            SELECT r.*, u.*
            FROM reviews r
            JOIN users u ON r.user_id = u.id
            WHERE r.product_id = ?
            ORDER BY r.created_at DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = mapUser(rs);
                Review review = mapReview(rs);
                map.put(user, review);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    // ================= REVIEW STATS =================
    public int[] getTotalReviews(int productId) {

        int[] re = new int[2];

        String sql = """
            SELECT COUNT(*) AS total_reviews,
                   AVG(rating) AS avg_rating
            FROM reviews
            WHERE product_id = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                re[0] = rs.getInt("total_reviews");
                double avg = rs.getDouble("avg_rating");
                re[1] = (int) Math.round(avg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return re;
    }

    // ================= ADD REVIEW =================
    public void addReview(Review rev) {

        String sql = """
            INSERT INTO reviews(user_id, product_id, rating, comment, created_at)
            VALUES (?, ?, ?, ?, NOW())
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, rev.getUserId());
            ps.setInt(2, rev.getProductId());
            ps.setInt(3, rev.getRating());
            ps.setString(4, rev.getComment());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean hasReviewed(int userId, int productId) {

        String sql = """
                    SELECT 1 FROM reviews
                    WHERE user_id = ? AND product_id = ?
                    LIMIT 1
                """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
