package Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OTPDAO {

    public void disableOldOTP(Integer userId, String email, String type) {

        String sql = "UPDATE otp SET status = FALSE " +
                "WHERE (user_id = ? OR email = ?) AND type = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (userId != null) ps.setInt(1, userId);
            else ps.setNull(1, java.sql.Types.INTEGER);

            ps.setString(2, email);
            ps.setString(3, type);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertOTP(Integer userId, String email, String otp, String type) {

        String sql = "INSERT INTO otp (user_id, email, otp, type, expired_at, status) " +
                "VALUES (?, ?, ?, ?, DATE_ADD(NOW(), INTERVAL 5 MINUTE), TRUE)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (userId != null) ps.setInt(1, userId);
            else ps.setNull(1, java.sql.Types.INTEGER);

            ps.setString(2, email);
            ps.setString(3, otp);
            ps.setString(4, type);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer findValidOTP(Integer userId, String email, String otp, String type) {

        String sql = "SELECT id FROM otp " +
                "WHERE (user_id = ? OR email = ?) " +
                "AND otp = ? AND type = ? " +
                "AND status = TRUE AND expired_at > NOW() " +
                "ORDER BY id DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (userId != null) ps.setInt(1, userId);
            else ps.setNull(1, java.sql.Types.INTEGER);

            ps.setString(2, email);
            ps.setString(3, otp);
            ps.setString(4, type);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void disableOTP(int id) {

        String sql = "UPDATE otp SET status = FALSE WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
