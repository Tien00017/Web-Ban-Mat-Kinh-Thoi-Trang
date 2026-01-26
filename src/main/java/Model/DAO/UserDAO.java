package Model.DAO;

import Model.Object.User;
import Model.Utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO {

    // Tìm user theo email + password đã hash
    public User findByEmailAndPassword(String email, String hashedPassword) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(User u) {
        String sql = """
                    INSERT INTO users
                    (full_name, display_name, avatar, email, phone, birth_date, gender, address, password, status, role, created_at)
                    VALUES (NULL, ?, NULL, ?, NULL, NULL, 0, NULL, ?, 1, 1, NOW())
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getDisplayName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT id FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(int userId, String hashedPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hashedPassword);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setId(id);
                u.setFullName(rs.getString("full_name"));
                u.setDisplayName(rs.getString("display_name"));
                u.setAvatar(rs.getString("avatar"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setBirthDate(rs.getDate("birth_date")); // Date
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
        return null;
    }

    public void updateProfile(User u) {
        String sql = """
                    UPDATE users
                    SET full_name = ?, 
                        display_name = ?, 
                        phone = ?, 
                        birth_date = ?, 
                        gender = ?, 
                        address = ?
                    WHERE id = ?
                """;

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getFullName());
            ps.setString(2, u.getDisplayName());
            ps.setString(3, u.getPhone());

            // Date → java.sql.Date
            if (u.getBirthDate() != null) {
                ps.setDate(4, new java.sql.Date(u.getBirthDate().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            ps.setInt(5, u.getGender());
            ps.setString(6, u.getAddress());
            ps.setInt(7, u.getId());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean changePassword(int userId, String oldHash, String newHash) {
        String sql = "UPDATE users SET password = ? WHERE id = ? AND password = ?";

        try (Connection con = DBContext.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newHash);
            ps.setInt(2, userId);
            ps.setString(3, oldHash);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // 1. Lấy danh sách user (cho AdminAccount)
    public List<User> findAll() {
        List<User> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM users
            ORDER BY id DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapUser(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // 2. Cập nhật trạng thái + vai trò
    public boolean updateStatusRole(int id, boolean status, int role) {
        String sql = "UPDATE users SET status = ?, role = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setInt(2, role);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
