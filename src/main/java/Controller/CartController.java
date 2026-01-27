package Controller;

import Model.Object.*;
import Model.Service.CartService;
import Model.Service.ProductImgService;
import Model.Service.ProductService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Cart", value = "/Cart")
public class CartController extends HttpServlet {

    private final CartService cartService = new CartService();
    private final ProductService productService = new ProductService();
    private final ProductImgService productImgService = new ProductImgService();

    private Cart getCart(HttpSession session, User user) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (user != null) ? getCart(session, user) : null;

        int totalQty = 0;
        double totalPrice = 0;

        if (cart != null) {
            request.setAttribute("cart", cart);
            totalQty = cartService.getTotalQuantity(cart);
            totalPrice = cartService.getTotalPrice(cart);
        }

        request.setAttribute("totalQty", totalQty);
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("/WEB-INF/Views/Cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        if ("add".equals(action)) {

            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                return;
            }

            Cart cart = getCart(session, user);
            addToCart(request, cart);

            response.setStatus(HttpServletResponse.SC_OK); // 200
            return;
        }

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        Cart cart = getCart(session, user);

        if (action == null) {
            response.sendRedirect("Cart");
            return;
        }

        switch (action) {
            case "add":
                addToCart(request, cart);
                break;
            case "increase":
                increaseItem(request, cart);
                break;
            case "decrease":
                decreaseItem(request, cart);
                break;
            case "update":
                updateQuantity(request, cart);
                break;
            case "remove":
                removeItem(request, cart);
                break;
            case "checkout":
                boolean ok = checkStock(cart, request);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                if (!ok) {
                    String msg = (String) request.getAttribute("error");

                    response.getWriter().write(
                            new Gson().toJson(
                                    Map.of(
                                            "ok", false,
                                            "message", msg
                                    )
                            )
                    );
                    return;
                }

                response.getWriter().write(
                        new Gson().toJson(
                                Map.of("ok", true)
                        )
                );
                return;

        }
        response.sendRedirect("Cart");
    }

    private void updateQuantity(HttpServletRequest request, Cart cart) {

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity;

        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));
        } catch (NumberFormatException e) {
            return;
        }

        if (quantity < 1) {
            quantity = 1;
        }

        cartService.updateQuantity(cart, productId, quantity);
    }

    // ===================== ACTION METHODS =====================

    private void addToCart(HttpServletRequest request, Cart cart) {
        int productId = Integer.parseInt(request.getParameter("productId"));

        Product product = productService.getProductDetail(productId);

        List<ProductImage> images = productImgService.getProductImages(product.getId());

        String mainImage = "Images/no-image.png";

        if (images != null && !images.isEmpty()) {
            mainImage = images.get(0).getImageUrl();
        }

        CartItem item = new CartItem(
                product.getId(),
                product.getProductName(),
                product.getPrice(),
                1,
                mainImage
        );

        cartService.addToCart(cart, item);
    }

    private void increaseItem(HttpServletRequest request, Cart cart) {
        int productId = Integer.parseInt(request.getParameter("productId"));
        cartService.increaseQuantity(cart, productId);
    }

    private void decreaseItem(HttpServletRequest request, Cart cart) {
        int productId = Integer.parseInt(request.getParameter("productId"));
        cartService.decreaseQuantity(cart, productId);
    }

    private void removeItem(HttpServletRequest request, Cart cart) {
        int productId = Integer.parseInt(request.getParameter("productId"));
        cartService.removeItem(cart, productId);
    }

    private boolean checkStock(Cart cart, HttpServletRequest request) {

        for (CartItem item : cart.getCartItems().values()) {

            int productId = item.getProductId();
            int cartQty = item.getQuantity();

            // số lượng còn trong kho
            int stockQty = productService.getStockQuantity(productId);

            if (cartQty > stockQty) {
                request.setAttribute(
                        "error",
                        "Sản phẩm " + item.getName() +
                                " chỉ còn " + stockQty +
                                " sản phẩm. Vui lòng giảm số lượng."
                );
                return false;
            }
        }
        return true;
    }

}
