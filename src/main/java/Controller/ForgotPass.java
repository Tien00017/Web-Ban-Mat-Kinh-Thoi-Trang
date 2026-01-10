package Controller;

import Model.Service.OTPService;
import Model.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ForgotPass", value = "/ForgotPass")
public class ForgotPass extends HttpServlet {

    private final UserService userService = new UserService();
    private final OTPService otpService = new OTPService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/ForgotPass");
            return;
        }

        switch (action) {
            case "sendOTP":
                handleSendOTP(request, response);
                break;
            case "verifyOTP":
                handleVerifyOTP(request, response);
                break;
            case "resetPass":
                handleResetPassword(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/ForgotPass");
        }
    }

    // ================= SEND OTP =================
    private void handleSendOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        request.setAttribute("email", email);

        try {
            otpService.sendOTP(email, "RESET_PASSWORD");

            request.setAttribute("otpSent", true);
            request.setAttribute("msg", "OTP đã được gửi qua email!");

        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
    }

    // ================= VERIFY OTP =================
    private void handleVerifyOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        request.setAttribute("email", email);
        request.setAttribute("otpSent", true);

        boolean valid = otpService.verifyOTP(email, otp, "RESET_PASSWORD");

        if (!valid) {
            request.setAttribute("error", "OTP không hợp lệ hoặc đã hết hạn!");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        request.setAttribute("otpVerified", true);
        request.setAttribute("msg", "Xác nhận OTP thành công!");

        request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
    }

    // ================= RESET PASSWORD =================
    private void handleResetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String newPw = request.getParameter("newPassword");
        String confirm = request.getParameter("confirm");

        request.setAttribute("email", email);
        request.setAttribute("otpVerified", true);

        if (!newPw.equals(confirm)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        try {
            userService.resetPasswordByEmail(email, newPw);
            response.sendRedirect(request.getContextPath() + "/Login");

        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
        }
    }
}
