package Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import Model.DAO.UserDAO;
import Model.Object.User;
import java.io.IOException;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String displayName = request.getParameter("displayName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        // Confirm password
        if (!password.equals(confirm)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
            return;
        }

        // Email trùng
        if (UserDAO.emailExists(email)) {
            request.setAttribute("error", "Email đã tồn tại. Vui lòng nhập email khác.");
            request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
            return;
        }

        // Tạo user object
        User u = new User();
        u.setDisplayName(displayName);
        u.setEmail(email);
        u.setPassword(password);

        // Đăng ký
        boolean created = UserDAO.register(u);

        if (created) {
            response.sendRedirect(request.getContextPath() + "/Login");
        } else {
            request.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại.");
            request.getRequestDispatcher("/WEB-INF/Views/Register.jsp").forward(request, response);
        }
    }
}