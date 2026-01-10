package Controller;

import Model.Object.Product;
import Model.Object.ProductImage;
import Model.Object.Review;
import Model.Object.User;
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

    @Override
    public void init() {
        productService = new ProductService();
        productImgService = new ProductImgService();
        reviewService = new ReviewService();
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

        // ===== 1. PRODUCT DETAIL =====
        Product product = productService.getProductDetail(productId);

        // ===== 2. PRODUCT IMAGES =====
        List<ProductImage> images = productImgService.getProductImages(productId);

        // ===== 3. REVIEW STATS =====
        int[] reviewStats = reviewService.getReviewStats(productId);

        // ===== 4. REVIEWS (User -> Review) =====
        Map<User, Review> reviews = reviewService.getReviews(productId);

        // ===== 5. RELATED PRODUCTS =====
        List<Product> relatedProducts = productService.getSimilarProducts(productId);
        List<Integer> ids = relatedProducts.stream()
                .map(Product::getId)
                .toList();
        Map<Integer, ProductImage> relatedImageMap = productImgService.getMainImages(ids);

        // ===== 6. SET ATTRIBUTE =====
        request.setAttribute("product", product);
        request.setAttribute("images", images);
        request.setAttribute("reviewStats", reviewStats);
        request.setAttribute("reviews", reviews);
        request.setAttribute("relatedProducts", relatedProducts);
        request.setAttribute("relatedImageMap", relatedImageMap);

        request.getRequestDispatcher("/WEB-INF/Views/ProductDetail.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}