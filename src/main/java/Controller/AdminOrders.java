package Controller;

import Model.Object.OrderAdminView;
import Model.Service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminOrders")
public class AdminOrders extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<OrderAdminView> orders = orderService.getAllAdminOrders();
        req.setAttribute("orders", orders);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminOrders.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        String idRaw = req.getParameter("id");

        if (action == null || idRaw == null || idRaw.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/AdminOrders");
            return;
        }

        try {
            int orderId = Integer.parseInt(idRaw.trim());
            orderService.updateOrderStatusByAction(orderId, action);
        } catch (Exception ignored) {}

        resp.sendRedirect(req.getContextPath() + "/AdminOrders");
    }
}
