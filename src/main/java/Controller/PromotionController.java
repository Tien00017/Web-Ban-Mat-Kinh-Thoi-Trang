package Controller;

import Model.Object.Promotion;
import Model.Object.Banner;
import Model.Object.Product;
import Model.Service.PromotionService;
import Model.Service.BannerService;
import Model.Service.PromotionProductService;
import Model.Service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/promotion")
public class PromotionController extends HttpServlet {

    private final PromotionService promotionService = new PromotionService();
    private final BannerService bannerService = new BannerService();
    private final PromotionProductService promotionProductService = new PromotionProductService();
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendRedirect("home");
            return;
        }

        int promotionId;
        try {
            promotionId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        Promotion promotion = promotionService.getById(promotionId);
        if (promotion == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Banner mainBanner = bannerService.getMainBanner(promotionId);
        List<Banner> banners = bannerService.getBannerByPromotionId(promotionId);

        List<Integer> productIds =
                promotionProductService.getProductIdsByPromotionId(promotionId);

        List<Product> products =
                productService.getProductsByIds(productIds);

        req.setAttribute("promotion", promotion);
        req.setAttribute("mainBanner", mainBanner);
        req.setAttribute("banners", banners);
        req.setAttribute("products", products);

        req.getRequestDispatcher("/promotion-detail.jsp")
                .forward(req, resp);
    }
}
