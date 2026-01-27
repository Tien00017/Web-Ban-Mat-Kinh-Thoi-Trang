package Model.Service;

import Model.DAO.UserDAO;
import Model.Object.User;
import Model.Utils.HashPass;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    // ================= LOGIN =================
    public User login(String email, String rawPassword) {

        String hashedPassword = HashPass.md5(rawPassword);

        User user = userDAO.findByEmailAndPassword(email, hashedPassword);

        if (user == null) {
            return null;
        }

        if (!user.isStatus()) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }

        return user;
    }

    // ================= REGISTER =================
    public void register(User u) {

        if (userDAO.emailExists(u.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        u.setPassword(HashPass.md5(u.getPassword()));

        boolean inserted = userDAO.insert(u);
        if (!inserted) {
            throw new RuntimeException("Không thể tạo tài khoản");
        }
    }

    // ================= PASSWORD =================
    public void updatePassword(int userId, String newPassword) {

        String hashed = HashPass.md5(newPassword);
        userDAO.updatePassword(userId, hashed);
    }

    public void resetPasswordByEmail(String email, String newPassword) {

        User user = userDAO.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Email không tồn tại");
        }

        updatePassword(user.getId(), newPassword);
    }

    // ================= QUERY =================
    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User loginWithGoogle(String email, String displayName) {

        User user = userDAO.findByEmail(email);

        if (user == null) {
            // chưa tồn tại → tạo mới
            user = new User();
            user.setEmail(email);
            user.setDisplayName(displayName);
            user.setStatus(true);
            user.setRole(1);

            userDAO.insert(user);
        }

        if (!user.isStatus()) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }

        return user;
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public void adminUpdateStatusRole(int targetUserId, boolean status, int role, User currentAdmin) {
        // currentAdmin là user đang đăng nhập (role=0)

        if (currentAdmin == null || currentAdmin.getRole() != 0) {
            throw new RuntimeException("Không có quyền admin");
        }

        // Rule 1: không cho admin tự khóa chính mình (khuyến nghị)
        if (currentAdmin.getId() == targetUserId && !status) {
            throw new RuntimeException("Bạn không thể tự khóa tài khoản admin của mình");
        }

        boolean ok = userDAO.updateStatusRole(targetUserId, status, role);
        if (!ok) {
            throw new RuntimeException("Cập nhật tài khoản thất bại");

        }

    }
    public User getUserById(int id) {
        return userDAO.getById(id);
    }

    public void updateProfile(User u) {
        // Có thể validate ở đây (phone, fullName không rỗng, …)
        userDAO.updateProfile(u);
    }
    public boolean changePassword(int userId, String oldRaw, String newRaw, String confirmRaw) {
        if (newRaw == null || confirmRaw == null || !newRaw.equals(confirmRaw)) {
            throw new IllegalArgumentException("confirm");
        }

        if (newRaw.trim().length() < 6) {
            throw new IllegalArgumentException("weak"); // optional
        }

        String oldHash = HashPass.md5(oldRaw);
        String newHash = HashPass.md5(newRaw);

        return userDAO.changePassword(userId, oldHash, newHash);
    }

}
