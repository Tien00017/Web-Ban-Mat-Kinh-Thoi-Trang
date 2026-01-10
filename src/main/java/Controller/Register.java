package Controller;

import Model.DAO.OTPDAO;
import Model.DAO.UserDAO;
import Model.Utils.Email;
import Model.Object.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "sendOTP":
                handleSendOTP(request, response);
                break;
            case "verifyOTP":
                handleVerifyOTP(request, response);
                break;
            case "register":
                handleRegister(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/Register");
        }
    }

    // ================== SEND OTP ==================
    private void handleSendOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        request.setAttribute("email", email);

        // 1. Check email tồn tại
        if (UserDAO.emailExists(email)) {
            request.setAttribute("error", "Đã tồn tại người dùng với email này!");
            // ❌ KHÔNG set otpSent
            request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
            return;
        }

        // 2. Sinh OTP
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);

        // 3. Lưu OTP
        OTPDAO.saveOTP(null, email, otp, "VERIFY_EMAIL");

        // 4. Gửi mail
        Email.send(email, "Xác nhận đăng ký", "Mã OTP của bạn là: " + otp);

        // 5. Gửi thành công → MỞ OTP
        request.setAttribute("otpSent", true);
        request.setAttribute("msg", "OTP đã được gửi tới email!");

        request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
    }

    // ================== VERIFY OTP ==================
    private void handleVerifyOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        request.setAttribute("email", email);
        request.setAttribute("otpSent", true); // giữ form OTP

        if (!OTPDAO.verifyOTP(null, email, otp, "VERIFY_EMAIL")) {
            request.setAttribute("error", "OTP không hợp lệ hoặc đã hết hạn!");
            request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
            return;
        }

        // ✅ OTP ĐÚNG → CHO PHÉP ĐĂNG KÝ
        request.setAttribute("otpVerified", true);
        request.setAttribute("msg", "Xác nhận email thành công!");

        request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
    }

    // ================== VERIFY REGISTER ==================
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String displayName = request.getParameter("displayName");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        request.setAttribute("email", email);
        request.setAttribute("otpVerified", true);

        if (!password.equals(confirm)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
            return;
        }

        User u = new User();
        u.setEmail(email);
        u.setDisplayName(displayName);
        u.setPassword(password); // 👉 sau này hash tại đây

        if (UserDAO.register(u)) {
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            request.setAttribute("error", "Đăng ký thất bại!");
            request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
        }
    }

}