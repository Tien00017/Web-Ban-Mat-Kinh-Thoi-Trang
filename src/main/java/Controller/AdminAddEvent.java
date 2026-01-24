package Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdminAddEvent", value = "/AdminAddEvent")
public class AdminAddEvent extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Views/Admin/AdminAddEvent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String discountType = req.getParameter("discountType");

        double discountValue = 0;
        if (req.getParameter("discountValue") != null &&
                !req.getParameter("discountValue").isEmpty()) {
            discountValue = Double.parseDouble(req.getParameter("discountValue"));
        }

        LocalDate startDate = LocalDate.parse(req.getParameter("startDate"));
        LocalDate endDate = LocalDate.parse(req.getParameter("endDate"));

        String status = "ACTIVE";

        // 1️⃣ Insert promotion
        int promotionId = PromotionDAO.insertPromotion(
                title, content, startDate, endDate,
                discountType, discountValue, status
        );

        // 2️⃣ Insert promotion_product
        String[] productIds = req.getParameterValues("productIds");
        if (productIds != null) {
            for (String pid : productIds) {
                PromotionDAO.insertPromotionProduct(
                        promotionId, Integer.parseInt(pid)
                );
            }
        }

        // 3️⃣ Upload banner
        Part bannerPart = req.getPart("banner");
        if (bannerPart != null && bannerPart.getSize() > 0) {
            String fileName = System.currentTimeMillis() + ".jpg";
            String uploadPath = req.getServletContext()
                    .getRealPath("/uploads/promotions");

            bannerPart.write(uploadPath + "/" + fileName);

            PromotionDAO.insertBanner(promotionId,
                    "/uploads/promotions/" + fileName, true);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/promotion/list");
    }
}