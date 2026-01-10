package Controller;

import Model.Object.*;
import Model.Service.CartService;
import Model.Service.ProductImgService;
import Model.Service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

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
        request.getRequestDispatcher("/WEB-INF/Views/Cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        Cart cart = getCart(session, user);
        String action = request.getParameter("action");

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
            case "remove":
                removeItem(request, cart);
                break;
        }

        response.sendRedirect("Cart");
    }

    // ===================== ACTION METHODS =====================

    private void addToCart(HttpServletRequest request, Cart cart) {
        int productId = Integer.parseInt(request.getParameter("productId"));

        Product product = productService.getProductDetail(productId);

        List<ProductImage> images = productImgService.getProductImages(product.getId());

        String mainImage = "src/main/webapp/Images/no-image.png";

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
}
