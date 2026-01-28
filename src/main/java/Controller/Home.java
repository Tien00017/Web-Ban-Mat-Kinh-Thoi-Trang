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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Home", value = "/Home")
public class Home extends HttpServlet {

    private PromotionService promotionService = new PromotionService();
    private BannerService bannerService = new BannerService();
    private ProductService productService = new ProductService();
    private ProductImgService productImgService = new ProductImgService();
    private PromotionProductService promotionProductService = new PromotionProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* ===== PROMOTION + BANNER  ===== */
        List<Promotion> promotions = promotionService.getAllActive();
        Map<Integer, List<Banner>> bannerMap = new HashMap<>();

        for (Promotion p : promotions) {
            List<Banner> banners = new ArrayList<>();
            banners.add(bannerService.getMainBanner(p.getId()));
            bannerMap.put(p.getId(), banners);
        }

        /* ===== BEST SELLING PRODUCTS ===== */
        List<Product> bestSellingProducts =
                productService.getBestSellingProducts(6);

        List<Integer> productIds = bestSellingProducts.stream()
                .map(Product::getId)
                .toList();

        Map<Integer, ProductImage> bestSellingImages =
                productImgService.getMainImages(productIds);

        Map<Integer, Double> salePriceMap =
                promotionProductService.getSalePriceMap(bestSellingProducts);

        Map<Integer, Double> discountMap =
                promotionProductService.getDiscountMap(productIds);

        /* ===== SET ATTRIBUTE ===== */
        request.setAttribute("promotions", promotions);
        request.setAttribute("bannerMap", bannerMap);
        request.setAttribute("bestSellingProducts", bestSellingProducts);
        request.setAttribute("bestSellingImages", bestSellingImages);
        request.setAttribute("salePriceMap", salePriceMap);
        request.setAttribute("discountMap", discountMap);

        request.getRequestDispatcher("/WEB-INF/Views/HomePage.jsp")
                .forward(request, response);
    }
}

