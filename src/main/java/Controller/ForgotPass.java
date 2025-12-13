package Controller;

import Model.Email.Utils;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import Model.DAO.UserDAO;
import Model.Object.User;

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

        if ("sendOTP".equals(action)) {
            handleSendOTP(request, response);
        } else if ("resetPass".equals(action)) {
            handleResetPassword(request, response);
        }
    }

    private void handleSendOTP(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        request.setAttribute("email", email);
        User u = UserDAO.getUserByEmail(email);

        if (u == null) {
            request.setAttribute("error", "Email không tồn tại!");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        // NGĂN admin gửi OTP
        if (u.getRole() == 0) { // 0 = admin
            request.setAttribute("error", "Tài khoản admin không thể lấy OTP!");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        // Random OTP
        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);

        // Lưu DB
        UserDAO.saveOTP(u.getId(), otp);

        // Gửi mail thật sự
        Utils.send(email, "Mã OTP đặt lại mật khẩu", "Mã OTP của bạn là: " + otp);

        request.setAttribute("msg", "OTP đã gửi qua email!");
        request.setAttribute("userId", u.getId());
        request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
    }

    private void handleResetPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");// lấy lại email từ form
        request.setAttribute("email", email);
        String otp = request.getParameter("otp");
        String newPw = request.getParameter("newPassword");
        String confirm = request.getParameter("confirm");

        // userId có thể null nếu form lỗi → kiểm tra an toàn
        String userIdStr = request.getParameter("userId");
        if (userIdStr == null || userIdStr.isEmpty()) {
            request.setAttribute("error", "Thiếu thông tin người dùng!");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        int userId = Integer.parseInt(userIdStr);

        // GỬI LẠI THÔNG TIN CHO JSP KHI LỖI
        request.setAttribute("email", email);
        request.setAttribute("userId", userId);

        // KIỂM TRA XÁC NHẬN MẬT KHẨU
        if (!newPw.equals(confirm)) {
            request.setAttribute("error", "Xác nhận mật khẩu không đúng");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        // KIỂM TRA OTP
        if (!UserDAO.verifyOTP(userId, otp)) {
            request.setAttribute("error", "OTP không hợp lệ hoặc đã hết hạn!");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
            return;
        }

        // UPDATE PASSWORD
        boolean ok = UserDAO.updatePassword(userId, newPw);

        if (ok) {
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            request.setAttribute("error", "Đổi mật khẩu thất bại!");
            request.getRequestDispatcher("/WEB-INF/Views/ForgotPass.jsp").forward(request, response);
        }
    }

}