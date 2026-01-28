package Controller;

import Model.Object.Dashboard;
import Model.Object.User;
import Model.Service.DashboardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Admin", value = "/Admin")
public class Admin extends HttpServlet {

    private final DashboardService dashboardService = new DashboardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User u = (User) req.getSession().getAttribute("user");
        if (u == null || u.getRole() != 0) { // admin=0
            resp.sendRedirect(req.getContextPath() + "/Login");
            return;
        }

        Dashboard stats = dashboardService.getDashboardStats();
        req.setAttribute("stats", stats);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/Admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}