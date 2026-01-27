package Controller;

import Model.Object.Product;
import Model.Object.ProductImage;
import Model.Object.Review;
import Model.Object.User;
import Model.Service.OrderService;
import Model.Service.ProductImgService;
import Model.Service.ProductService;
import Model.Service.ReviewService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductDetail", value = "/ProductDetail")
public class ProductDetail extends HttpServlet {

    private ProductService productService ;
    private ProductImgService productImgService;
    private ReviewService reviewService;
    private OrderService orderService;


    @Override
    public void init() {
        productService = new ProductService();
        productImgService = new ProductImgService();
        reviewService = new ReviewService();
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // ===== 1. PRODUCT DETAIL =====
        Product product = productService.getProductDetail(productId);

        // ===== 2. PRODUCT IMAGES =====
        List<ProductImage> images = productImgService.getProductImages(productId);

        // ===== 3. REVIEW STATS =====
        int[] reviewStats = reviewService.getReviewStats(productId);

        // ===== 4. REVIEWS =====
        Map<User, Review> reviews = reviewService.getReviews(productId);

        // ===== 5. RELATED PRODUCTS =====
        List<Product> relatedProducts = productService.getSimilarProducts(productId);
        List<Integer> ids = relatedProducts.stream()
                .map(Product::getId)
                .toList();
        Map<Integer, ProductImage> relatedImageMap = productImgService.getMainImages(ids);

        // ===== 6. CAN REVIEW LOGIC =====
        boolean canReview = false;
        if (user != null) {
            boolean hasPurchased =
                    orderService.hasPurchased(user.getId(), productId);
            boolean hasReviewed =
                    reviewService.hasReviewed(user.getId(), productId);

            canReview = hasPurchased && !hasReviewed;
        }

        // ===== 7. SET ATTRIBUTE =====
        request.setAttribute("product", product);
        request.setAttribute("images", images);
        request.setAttribute("reviewStats", reviewStats);
        request.setAttribute("reviews", reviews);
        request.setAttribute("relatedProducts", relatedProducts);
        request.setAttribute("relatedImageMap", relatedImageMap);
        request.setAttribute("canReview", canReview);

        request.getRequestDispatcher("/WEB-INF/Views/ProductDetail.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        int productId;
        int rating;
        String comment;

        try {
            productId = Integer.parseInt(request.getParameter("productId"));
            rating = Integer.parseInt(request.getParameter("rating"));
            comment = request.getParameter("comment");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }

        // ===== CHECK ĐIỀU KIỆN =====
        boolean hasPurchased = orderService.hasPurchased(user.getId(), productId);
        boolean hasReviewed = reviewService.hasReviewed(user.getId(), productId);

        if (!hasPurchased || hasReviewed) {
            response.sendRedirect(
                    request.getContextPath() + "/ProductDetail?id=" + productId
            );
            return;
        }

        // ===== CREATE REVIEW OBJECT =====
        Review rev = new Review();
        rev.setUserId(user.getId());
        rev.setProductId(productId);
        rev.setRating(rating);
        rev.setComment(comment);

        // ===== SAVE =====
        reviewService.addReview(rev);

        // ===== REDIRECT =====
        response.sendRedirect(
                request.getContextPath() + "/ProductDetail?id=" + productId
        );
    }
}