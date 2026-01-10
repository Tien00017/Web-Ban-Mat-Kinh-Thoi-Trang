package Controller;

import Model.Service.OTPService;
import Model.Service.UserService;
import Model.Object.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {

    private final UserService userService = new UserService();
    private final OTPService otpService = new OTPService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/Register");
            return;
        }

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

        try {
            // gửi OTP qua Service (Service tự check email tồn tại)
            otpService.sendOTP(email, "REGISTER");

            request.setAttribute("otpSent", true);
            request.setAttribute("msg", "OTP đã được gửi tới email!");

        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
    }

    // ================== VERIFY OTP ==================
    private void handleVerifyOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        request.setAttribute("email", email);
        request.setAttribute("otpSent", true);

        boolean valid = otpService.verifyOTP(email, otp, "REGISTER");

        if (!valid) {
            request.setAttribute("error", "OTP không hợp lệ hoặc đã hết hạn!");
            request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
            return;
        }

        request.setAttribute("otpVerified", true);
        request.setAttribute("msg", "Xác nhận email thành công!");

        request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
    }

    // ================== REGISTER ==================
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

        User user = new User();
        user.setEmail(email);
        user.setDisplayName(displayName);
        user.setPassword(password);

        try {
            userService.register(user);

            // nếu không exception → chắc chắn thành công
            response.sendRedirect(request.getContextPath() + "/Login");

        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/Views/Register.jsp")
                    .forward(request, response);
        }

    }

}
