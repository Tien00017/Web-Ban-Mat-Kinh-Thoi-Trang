package Controller;

import Model.DAO.UserDAO;
import Model.Object.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminAccount")
public class AdminAccount extends HttpServlet {

    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<User> users = userDAO.findAll();
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
            int id = Integer.parseInt(req.getParameter("id"));
            boolean status = "1".equals(req.getParameter("status")); // 1 mở, 0 khóa
            int role = Integer.parseInt(req.getParameter("role"));    // 0 user, 1 admin

            userDAO.updateStatusRole(id, status, role);
        }

        resp.sendRedirect(req.getContextPath() + "/AdminAccount");
    }
}