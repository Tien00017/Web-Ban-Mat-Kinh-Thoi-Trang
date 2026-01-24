package Controller;

import Model.DAO.UserDAO;
import Model.Object.User;
import Model.Service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Views/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User u = userService.login(email, password);

            if (u == null) {
                request.setAttribute("error", "Sai email hoặc mật khẩu!");
                request.getRequestDispatcher("/WEB-INF/Views/Login.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", u);

            response.sendRedirect(
                    request.getContextPath() + (u.getRole() == 0 ? "/Admin" : "/Home")
            );

        } catch (RuntimeException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/Views/Login.jsp").forward(request, response);
        }
    }
}
