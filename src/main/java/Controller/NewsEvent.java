package Controller;

import Model.Object.Banner;
import Model.Object.Product;
import Model.Object.ProductImage;
import Model.Object.Promotion;
import Model.Service.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet(name = "NewsEvent", value = "/NewsEvent")
public class NewsEvent extends HttpServlet {

    private PromotionService promotionService = new PromotionService();
    private BannerService bannerService = new BannerService();
    private PromotionProductService promotionProductService = new PromotionProductService();
    private ProductService productService = new ProductService();
    private ProductImgService productImgService = new ProductImgService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ===== 1. CHECK PARAM ===== */
        String idRaw = request.getParameter("id");
        if (idRaw == null) {
            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }

        int promotionId;
        try {
            promotionId = Integer.parseInt(idRaw);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }

        /* ===== 2. PROMOTION ===== */
        Promotion promotion = promotionService.getById(promotionId);
        if (promotion == null) {
            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }

        /* ===== 3. BANNER ===== */
        List<Banner> banners =
                bannerService.getBannerByPromotionId(promotionId);

        /* ===== 4. PRODUCT IDS ===== */
        List<Integer> productIds =
                promotionProductService.getProductIdsByPromotionId(promotionId);

        /* ===== 5. PRODUCT LIST + SALE PRICE MAP ===== */
        List<Product> products = new ArrayList<>();
        Map<Integer, Double> salePriceMap = new HashMap<>();

        if (productIds != null && !productIds.isEmpty()) {
            for (int productId : productIds) {
                try {
                    Product p = productService.getProductDetail(productId);
                    products.add(p);

                    // ===== TÍNH SALE PRICE =====
                    double salePrice = p.getPrice();

                    if ("PERCENT".equalsIgnoreCase(promotion.getDiscountType())) {
                        salePrice = p.getPrice()
                                * (1 - promotion.getDiscountValue() / 100.0);
                    } else if ("AMOUNT".equalsIgnoreCase(promotion.getDiscountType())) {
                        salePrice = p.getPrice() - promotion.getDiscountValue();
                    }

                    if (salePrice < 0) salePrice = 0;

                    salePriceMap.put(p.getId(), salePrice);

                } catch (Exception ignored) {
                    // product lỗi / bị xoá thì bỏ
                }
            }
        }

        /* ===== 6. MAIN IMAGE MAP ===== */
        Map<Integer, ProductImage> mainImages =
                productImgService.getMainImages(productIds);

        /* ===== 7. SET ATTRIBUTE ===== */
        request.setAttribute("promotion", promotion);
        request.setAttribute("banners", banners);
        request.setAttribute("products", products);
        request.setAttribute("mainImages", mainImages);
        request.setAttribute("salePriceMap", salePriceMap);

        /* ===== 8. FORWARD ===== */
        request.getRequestDispatcher("/WEB-INF/Views/NewsEvent.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
