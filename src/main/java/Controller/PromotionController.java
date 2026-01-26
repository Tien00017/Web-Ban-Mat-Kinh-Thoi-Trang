package Controller;

import Model.DAO.ProductDAO;
import Model.DAO.PromotionDAO;
import Model.Object.Product;
import Model.Object.Promotion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/admin/event/*")
public class PromotionController extends HttpServlet {

    private PromotionDAO promotionDAO = new PromotionDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            session.setAttribute("user", "TEST_ADMIN");
            session.setAttribute("role", "ADMIN"); // nếu project bạn kiểm tra role
        }

        String action = req.getPathInfo();

        if (action == null || action.equals("/list")) {
            list(req, resp);
        }
        else if (action.equals("/add")) {
            showAddForm(req, resp);
        }
        else if (action.equals("/edit")) {
            showEditForm(req, resp);
        }
        else if (action.equals("/delete")) {
            delete(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            session.setAttribute("user", "TEST_ADMIN");
            session.setAttribute("role", "ADMIN"); // nếu project bạn kiểm tra role
        }

        String action = req.getPathInfo();
        if (action == null) action = "";
        if (action.equals("/create")) {
            create(req, resp);
        }
        else if (action.equals("/update")) {
            update(req, resp);
        }
    }

    // ================== METHODS ==================

    private void list(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Promotion> list = promotionDAO.findAll();
        req.setAttribute("events", list);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminListEvent.jsp")
                .forward(req, resp);
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Product> products = productDAO.findAll();
        req.setAttribute("products", products);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminAddEvent.jsp")
                .forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        Promotion event = promotionDAO.findById(id);
        List<Product> products = productDAO.findAll();
        List<Integer> selectedProductIds = promotionDAO.getProductIdsByPromotionId(id);

        req.setAttribute("event", event);
        req.setAttribute("products", products);
        req.setAttribute("selectedProductIds", selectedProductIds);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminEditEvent.jsp")
                .forward(req, resp);
    }


    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Promotion p = new Promotion();
        p.setTitle(req.getParameter("title"));
        p.setContent(req.getParameter("content"));
        p.setStartDate(Date.valueOf(req.getParameter("startDate")));
        p.setEndDate(Date.valueOf(req.getParameter("endDate")));
        p.setDiscountType(req.getParameter("discountType"));

        String dv = req.getParameter("discountValue");
        if (dv == null || dv.trim().isEmpty()) dv = "0";   // tránh NumberFormatException
        p.setDiscountValue(Double.parseDouble(dv));

        p.setStatus("ACTIVE");

        // 1) insert promotions -> lấy id mới
        int promotionId = promotionDAO.insert(p);

        // 2) lấy mảng checkbox
        String[] pidStrs = req.getParameterValues("productIds");
        int[] pids = null;
        if (pidStrs != null) {
            pids = new int[pidStrs.length];
            for (int i = 0; i < pidStrs.length; i++) pids[i] = Integer.parseInt(pidStrs[i]);
        }

        promotionDAO.insertWithProducts(p, pids);

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

        // 1) update promotions
        promotionDAO.update(p);

        String[] pidStrs = req.getParameterValues("productIds");
        int[] pids = null;
        if (pidStrs != null) {
            pids = new int[pidStrs.length];
            for (int i = 0; i < pidStrs.length; i++) pids[i] = Integer.parseInt(pidStrs[i]);
        }
        promotionDAO.updateWithProducts(p, pids);

        resp.sendRedirect(req.getContextPath() + "/admin/event/list");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        promotionDAO.delete(id);
        resp.sendRedirect(req.getContextPath() + "/admin/event/list");
    }
}