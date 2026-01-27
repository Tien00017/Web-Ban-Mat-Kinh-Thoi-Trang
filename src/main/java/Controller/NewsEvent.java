package Controller;

import Model.Object.Promotion;
import Model.Service.PromotionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/NewsEvent")
public class NewsEvent extends HttpServlet {

    private final PromotionService promotionService = new PromotionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Promotion promotion = promotionService.getActivePromotion();

        if (promotion != null) {
            request.setAttribute("promotion", promotion);
        }

        request.getRequestDispatcher("/WEB-INF/Views/NewsEvent.jsp")
               .forward(request, response);
    }
}
