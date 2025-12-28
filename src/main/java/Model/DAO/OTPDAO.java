package Model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OTPDAO {
    // ================= SAVE OTP =================
    public static void saveOTP(Integer userId, String email, String otp, String type) {

        String disableOld = "UPDATE otp SET status = FALSE " +
                "WHERE (user_id = ? OR email = ?) AND type = ?";

        String insertNew = "INSERT INTO otp (user_id, email, otp, type, expired_at, status) " +
                "VALUES (?, ?, ?, ?, DATE_ADD(NOW(), INTERVAL 5 MINUTE), TRUE)";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement ps1 = conn.prepareStatement(disableOld)) {
                if (userId != null) ps1.setInt(1, userId);
                else ps1.setNull(1, java.sql.Types.INTEGER);

                ps1.setString(2, email);
                ps1.setString(3, type);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = conn.prepareStatement(insertNew)) {
                if (userId != null) ps2.setInt(1, userId);
                else ps2.setNull(1, java.sql.Types.INTEGER);

                ps2.setString(2, email);
                ps2.setString(3, otp);
                ps2.setString(4, type);
                ps2.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= VERIFY OTP =================
    public static boolean verifyOTP(Integer userId, String email, String otp, String type) {

        String sql = "SELECT id FROM otp " +
                "WHERE (user_id = ? OR email = ?) " +
                "AND otp = ? AND type = ? AND status = TRUE AND expired_at > NOW() " +
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
                disableOTP(rs.getInt("id"));
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= DISABLE =================
    private static void disableOTP(int id) {
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
