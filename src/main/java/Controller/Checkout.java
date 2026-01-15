package Controller;

import Model.Object.Cart;
import Model.Object.User;
import Model.Service.CartService;
import Model.Service.OrderItemService;
import Model.Service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "Checkout", value = "/Checkout")
public class Checkout extends HttpServlet {
    private final CartService cartService = new CartService();
    private final OrderService orderService = new OrderService();
    private final OrderItemService orderItemService = new OrderItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getCartItems().isEmpty()) {
            response.sendRedirect("Cart");
            return;
        }

        request.setAttribute("cart", cart);
        request.setAttribute("totalQty", cartService.getTotalQuantity(cart));
        request.setAttribute("totalPrice", cartService.getTotalPrice(cart));

        request.getRequestDispatcher("/WEB-INF/Views/Checkout.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        if (user == null || cart == null || cart.getCartItems().isEmpty()) {
            response.sendRedirect("Cart");
            return;
        }

        String name = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String paymentMethod = request.getParameter("pay");

        int orderId = orderService.createOrder(
                user.getId(),
                name,
                phone,
                address,
                paymentMethod,
                cartService.getTotalPrice(cart)
        );

        if (orderId > 0) {
            orderItemService.insertOrderItems(orderId, cart);
            session.removeAttribute("cart");
            response.sendRedirect("OrderHistory");
        } else {
            response.sendRedirect("Checkout?error=fail");
        }
    }

}
