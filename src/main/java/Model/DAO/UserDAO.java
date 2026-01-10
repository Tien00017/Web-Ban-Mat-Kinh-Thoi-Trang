package Model.DAO;

import Model.Object.User;
import Model.Utils.HashPass;

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
            ps.setString(2, HashPass.md5(password));

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
            ps.setString(3, HashPass.md5(u.getPassword()));

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

    //UPDATE PASSWORD
    public static boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, HashPass.md5(newPassword));
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerGoogle(User u) {
        String sql = """
        INSERT INTO users
        (display_name, email, password, status, role, created_at)
        VALUES (?, ?, NULL, 1, 1, NOW())
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getDisplayName());
            ps.setString(2, u.getEmail());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
