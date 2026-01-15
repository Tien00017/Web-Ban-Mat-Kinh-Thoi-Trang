package Controller;

import Model.Object.Order;
import Model.Object.OrderItem;
import Model.Object.Product;
import Model.Object.User;
import Model.Service.OrderItemService;
import Model.Service.OrderService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "OrderHistory", value = "/OrderHistory")
public class OrderHistory extends HttpServlet {
    private final OrderService orderService = new OrderService();
    private final OrderItemService orderItemService = new OrderItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        // 1. Orders
        List<Order> orders =
                orderService.getOrdersByUserId(user.getId());

        // 2. orderId -> List<OrderItem>
        Map<Integer, List<OrderItem>> orderItemsMap =
                orderItemService.getOrderItemsMap(orders);

        // 3. orderItemId -> (Product -> imageUrl)
        Map<Integer, Map<Product, String>> orderItemProductMap =
                orderItemService.buildOrderItemForProductMap(orderItemsMap);

        request.setAttribute("orders", orders);
        request.setAttribute("orderItemsMap", orderItemsMap);
        request.setAttribute("orderItemProductMap", orderItemProductMap);

        request.getRequestDispatcher("/WEB-INF/Views/OrderHistory.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}