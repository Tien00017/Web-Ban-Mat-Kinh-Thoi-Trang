package Controller;

import Model.Object.User;
import Model.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminAccount")
public class AdminAccount extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<User> users = userService.getAllUsers();
        req.setAttribute("users", users);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminAccount.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "";

        if ("update".equals(action)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean status = "1".equals(req.getParameter("status"));
                int role = Integer.parseInt(req.getParameter("role"));

                User current = (User) req.getSession().getAttribute("user"); // admin role=0
                userService.adminUpdateStatusRole(id, status, role, current);
            } catch (Exception ignored) {}

        }

        resp.sendRedirect(req.getContextPath() + "/AdminAccount");
    }
}