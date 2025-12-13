package Model.DAO;

import Model.Object.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // LOGIN
    public static User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ? AND status = 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();

                u.setId(rs.getInt("id"));
                u.setFullName(rs.getString("full_name"));
                u.setDisplayName(rs.getString("display_name"));
                u.setAvatar(rs.getString("avatar"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setBirthDate(rs.getDate("birth_date"));
                u.setGender(rs.getInt("gender"));
                u.setAddress(rs.getString("address"));
                u.setPassword(rs.getString("password"));
                u.setStatus(rs.getBoolean("status"));
                u.setRole(rs.getInt("role"));
                u.setCreatedAt(rs.getTimestamp("created_at"));

                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Login fail
    }

    // REGISTER
    public static boolean register(User u) {
        String sql = "INSERT INTO users (full_name, display_name, avatar, email, phone, birth_date, gender, address, password, status, role, created_at) " +
                "VALUES (NULL, ?, NULL, ?, NULL, NULL, 0, NULL, ?, 1, 1, NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getDisplayName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // CHECK EMAIL EXIST
    public static boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();    // true = email đã tồn tại
        } catch (Exception e) {
            e.printStackTrace();
            return false; // lỗi DB
        }

    }

    //GET USER BY EMAIL
    public static User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setDisplayName(rs.getString("display_name"));
                u.setRole(rs.getInt("role"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //SAVE OTP
    public static void saveOTP(int userId, String otp) {
        String disableOld = "UPDATE password_reset SET status = FALSE WHERE user_id = ?";
        String insertNew = "INSERT INTO password_reset (user_id, otp, status, expired_at) " +
                "VALUES (?, ?, TRUE, DATE_ADD(NOW(), INTERVAL 5 MINUTE))";

        try (Connection conn = DBConnection.getConnection()) {

            // Vô hiệu hóa OTP cũ
            try (PreparedStatement ps1 = conn.prepareStatement(disableOld)) {
                ps1.setInt(1, userId);
                ps1.executeUpdate();
            }

            // Chèn OTP mới
            try (PreparedStatement ps2 = conn.prepareStatement(insertNew)) {
                ps2.setInt(1, userId);
                ps2.setString(2, otp);
                ps2.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //VERIFY OTP
    public static boolean verifyOTP(int userId, String otp) {

        String sql = "SELECT id FROM password_reset " +
                "WHERE user_id = ? AND otp = ? AND status = TRUE AND expired_at > NOW() " +
                "ORDER BY id DESC LIMIT 1";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, otp);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Nếu OTP đúng → vô hiệu hóa luôn
                disableOTP(rs.getInt("id"));
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //disable OTP
    private static void disableOTP(int otpId) {
        String sql = "UPDATE password_reset SET status = FALSE WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, otpId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //UPDATE PASSWORD
    public static boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
