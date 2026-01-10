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
                re[1] = rs.getInt("avg_rating"); // làm tròn phía JSP
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

    // ================= MAP REVIEW =================
    private Review mapReview(ResultSet rs) throws Exception {
        Review r = new Review();
        r.setId(rs.getInt("id"));
        r.setUserId(rs.getInt("user_id"));
        r.setProductId(rs.getInt("product_id"));
        r.setRating(rs.getInt("rating"));
        r.setComment(rs.getString("comment"));
        r.setCreatedAt(rs.getTimestamp("created_at"));
        return r;
    }
}
