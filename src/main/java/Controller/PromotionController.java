package Controller;

import Model.Object.Product;
import Model.Object.Promotion;
import Model.Service.PromotionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/admin/event/*")
public class PromotionController extends HttpServlet {

    private final PromotionService promotionService = new PromotionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getPathInfo();

        if (action == null || action.equals("/list")) {
            list(req, resp);
        } else if (action.equals("/add")) {
            showAddForm(req, resp);
        } else if (action.equals("/edit")) {
            showEditForm(req, resp);
        } else if (action.equals("/delete")) {
            delete(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/event/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getPathInfo();
        if (action == null) action = "";

        if (action.equals("/create")) {
            create(req, resp);
        } else if (action.equals("/update")) {
            update(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/event/list");
        }
    }

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Promotion> list = promotionService.getAllPromotions();
        req.setAttribute("events", list);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminListEvent.jsp")
                .forward(req, resp);
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Product> products = promotionService.getAllProducts();
        req.setAttribute("products", products);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminAddEvent.jsp")
                .forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        Promotion event = promotionService.getPromotionById(id);
        List<Product> products = promotionService.getAllProducts();
        List<Integer> selectedProductIds = promotionService.getSelectedProductIds(id);

        req.setAttribute("event", event);
        req.setAttribute("products", products);
        req.setAttribute("selectedProductIds", selectedProductIds);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminEditEvent.jsp")
                .forward(req, resp);
    }

    private int[] parseProductIds(HttpServletRequest req) {
        String[] pidStrs = req.getParameterValues("productIds");
        if (pidStrs == null || pidStrs.length == 0) return null;

        int[] pids = new int[pidStrs.length];
        for (int i = 0; i < pidStrs.length; i++) {
            pids[i] = Integer.parseInt(pidStrs[i]);
        }
        return pids;
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Promotion p = new Promotion();
        p.setTitle(req.getParameter("title"));
        p.setContent(req.getParameter("content"));
        p.setStartDate(Date.valueOf(req.getParameter("startDate")));
        p.setEndDate(Date.valueOf(req.getParameter("endDate")));
        p.setDiscountType(req.getParameter("discountType"));

        String dv = req.getParameter("discountValue");
        if (dv == null || dv.trim().isEmpty()) dv = "0";
        p.setDiscountValue(Double.parseDouble(dv));

        p.setStatus("ACTIVE");

        int[] productIds = parseProductIds(req);
        promotionService.createPromotionWithProducts(p, productIds);

        resp.sendRedirect(req.getContextPath() + "/admin/event/list");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int promotionId = Integer.parseInt(req.getParameter("id"));

        Promotion p = new Promotion();
        p.setId(promotionId);
        p.setTitle(req.getParameter("title"));
        p.setContent(req.getParameter("content"));
        p.setStartDate(Date.valueOf(req.getParameter("startDate")));
        p.setEndDate(Date.valueOf(req.getParameter("endDate")));
        p.setDiscountType(req.getParameter("discountType"));

        String dv = req.getParameter("discountValue");
        if (dv == null || dv.trim().isEmpty()) dv = "0";
        p.setDiscountValue(Double.parseDouble(dv));

        p.setStatus(req.getParameter("status"));

        int[] productIds = parseProductIds(req);
        promotionService.updatePromotionWithProducts(p, productIds);

        resp.sendRedirect(req.getContextPath() + "/admin/event/list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        promotionService.deletePromotion(id);
        resp.sendRedirect(req.getContextPath() + "/admin/event/list");
    }
}
