package Controller;

import Model.Object.Banner;
import Model.Object.Promotion;
import Model.Service.BannerService;
import Model.Service.PromotionService;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Lấy danh sách promotion
        List<Promotion> promotions = promotionService.getAllActive();

        // 2. Với mỗi promotion → lấy banner
        Map<Integer, List<Banner>> bannerMap = new HashMap<>();

        for (Promotion p : promotions) {
            List<Banner> banners = new ArrayList<>();
            banners.add(bannerService.getMainBanner(p.getId()));
            bannerMap.put(p.getId(), banners);
        }

        // 3. Đẩy sang JSP
        request.setAttribute("promotions", promotions);
        request.setAttribute("bannerMap", bannerMap);

        request.getRequestDispatcher("/WEB-INF/Views/HomePage.jsp")
                .forward(request, response);
    }
}

