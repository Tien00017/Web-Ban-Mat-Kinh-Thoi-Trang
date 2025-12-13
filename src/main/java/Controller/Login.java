package Controller;

import Model.DAO.UserDAO;
import Model.Object.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Views/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User u = UserDAO.login(email, password);

        if (u != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", u);

            // Kiểm tra role
            if (u.getRole() == 0) { // role = 0 là admin
                response.sendRedirect(request.getContextPath() + "/Admin");
            } else { // role = 1 là khách hàng
                response.sendRedirect(request.getContextPath() + "/Home");
            }
        } else {
            request.setAttribute("error", "Sai email hoặc mật khẩu!");
            request.getRequestDispatcher("/WEB-INF/Views/Login.jsp").forward(request, response);
        }
    }
}
