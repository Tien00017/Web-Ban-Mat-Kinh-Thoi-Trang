package Model.Service;

import Model.DAO.UserDAO;
import Model.Object.User;
import Model.Utils.HashPass;

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
}
