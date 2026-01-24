package Model.Service;

import Model.DAO.OTPDAO;
import Model.DAO.UserDAO;
import Model.Object.User;
import Model.Utils.Email;

public class OTPService {

    private final OTPDAO otpDAO = new OTPDAO();
    private final UserDAO userDAO = new UserDAO();

    // ================= SEND OTP (REGISTER / RESET PASSWORD) =================
    public void sendOTP(String email, String type) {

        User user = userDAO.findByEmail(email);

        switch (type) {

            case "REGISTER":
                // Email đăng ký phải CHƯA tồn tại
                if (user != null) {
                    throw new RuntimeException("Email đã tồn tại!");
                }
                sendAndSaveOTP(null, email, type);
                break;

            case "RESET_PASSWORD":
                // Email phải tồn tại và là USER (role = 1)
                if (user == null) {
                    throw new RuntimeException("Email không tồn tại!");
                }
                if (user.getRole() != 1) {
                    throw new RuntimeException("Tài khoản này không được phép đổi mật khẩu!");
                }
                sendAndSaveOTP(user.getId(), email, type);
                break;

            default:
                throw new RuntimeException("Loại OTP không hợp lệ!");
        }
    }

    // ================= VERIFY OTP =================
    public boolean verifyOTP(String email, String otp, String type) {

        User user = userDAO.findByEmail(email);
        Integer userId = (user != null) ? user.getId() : null;

        Integer otpId = otpDAO.findValidOTP(userId, email, otp, type);

        if (otpId == null) {
            return false;
        }

        otpDAO.disableOTP(otpId);
        return true;
    }

    // ================= CORE SAVE + SEND =================
    private void sendAndSaveOTP(Integer userId, String email, String type) {

        String otp = generateOTP();

        otpDAO.disableOldOTP(userId, email, type);
        otpDAO.insertOTP(userId, email, otp, type);

        Email.send(
                email,
                "Mã xác thực OTP",
                "Mã OTP của bạn là: " + otp
        );
    }

    // ================= UTIL =================
    private String generateOTP() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }
}
