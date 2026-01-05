package Controller;

import Model.DAO.OTPDAO;
import Model.DAO.UserDAO;
import Model.Object.User;
import Model.Utils.Email;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ForgotPass", value = "/ForgotPass")
public class ForgotPass extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
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
            case "resetPass":
                handleResetPassword(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/ForgotPass");
        }
    }

    private void handleSendOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        request.setAttribute("email", email);

        User u = UserDAO.getUserByEmail(email);
        if (u == null || u.getRole() == 0) {
            request.setAttribute("error", "Email không hợp lệ!");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
        OTPDAO.saveOTP(u.getId(), null, otp, "RESET_PASSWORD");

        Email.send(email, "Mã OTP đặt lại mật khẩu", "OTP của bạn là: " + otp);

        request.setAttribute("otpSent", true);
        request.setAttribute("userId", u.getId());
        request.setAttribute("msg", "OTP đã gửi qua email!");

        request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
    }

    private void handleVerifyOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        User u = UserDAO.getUserByEmail(email);
        request.setAttribute("email", email);
        request.setAttribute("otpSent", true);
        request.setAttribute("userId", u.getId());

        if (!OTPDAO.verifyOTP(u.getId(), null, otp, "RESET_PASSWORD")) {
            request.setAttribute("error", "OTP không hợp lệ hoặc hết hạn!");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        request.setAttribute("otpVerified", true);
        request.setAttribute("msg", "Xác nhận OTP thành công!");

        request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
    }

    private void handleResetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String newPw = request.getParameter("newPassword");
        String confirm = request.getParameter("confirm");

        User u = UserDAO.getUserByEmail(email);

        if (!newPw.equals(confirm)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.setAttribute("otpVerified", true);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        UserDAO.updatePassword(u.getId(), newPw);
        response.sendRedirect(request.getContextPath() + "/Login");
    }


}