package Controller;

import Model.DAO.PromotionDAO;
import Model.Object.Product;
import Model.Object.Promotion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(name = "Promotion", value = "/Promotion")
public class PromotionController extends HttpServlet {

    private PromotionDAO dao = new PromotionDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String path = req.getPathInfo();

        if (path == null || path.equals("/list")) {
            list(req, resp);
        } else if (path.equals("/add")) {
            showAddForm(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getPathInfo().equals("/create")) {
            create(req, resp);
        }
    }
    private void showAddForm(...) {
        List<Product> products = ProductDAO.findAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/admin/promotion/add.jsp")
                .forward(req, resp);
    }
    private void create(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Promotion p = new Promotion();
        p.setTitle(req.getParameter("title"));
        p.setContent(req.getParameter("content"));
        p.setStartDate(Date.valueOf(req.getParameter("startDate")));
        p.setEndDate(Date.valueOf(req.getParameter("endDate")));
        p.setDiscountType(req.getParameter("discountType"));
        p.setDiscountValue(Double.parseDouble(req.getParameter("discountValue")));
        p.setStatus("ACTIVE");

        int promotionId = dao.insert(p);

        String[] productIds = req.getParameterValues("productIds");
        if (productIds != null) {
            for (String id : productIds) {
                dao.insertPromotionProduct(
                        promotionId, Integer.parseInt(id)
                );
            }
        }

        Part banner = req.getPart("banner");
        if (banner.getSize() > 0) {
            String fileName = System.currentTimeMillis() + ".jpg";
            banner.write(getServletContext()
                    .getRealPath("/uploads/promotions/" + fileName));
            dao.insertBanner(promotionId,
                    "/uploads/promotions/" + fileName);
        }

        resp.sendRedirect(req.getContextPath()
                + "/admin/promotion/list");
    }
}