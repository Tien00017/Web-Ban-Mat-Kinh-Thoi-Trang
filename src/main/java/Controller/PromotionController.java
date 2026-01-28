package Controller;

import Model.Object.Product;
import Model.Object.Promotion;
import Model.Service.PromotionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Date;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
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
            throws ServletException, IOException {

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

        String mainBannerUrl = promotionService.getMainBannerUrl(id);
        List<String> extraBannerUrls = promotionService.getExtraBannerUrls(id);
        req.setAttribute("mainBannerUrl", mainBannerUrl);
        req.setAttribute("extraBannerUrls", extraBannerUrls);

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

    private void create(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Promotion p = buildPromotionFromRequest(req, false);

        int[] productIds = parseProductIds(req);

        // banner: ưu tiên file, nếu không có file thì dùng URL nhập
        String mainBannerUrl = req.getParameter("mainBannerUrl");
        Part mainBannerFile = req.getPart("mainBannerFile");

        String mainBannerPath = saveBannerFile(mainBannerFile, req);
        if (mainBannerPath == null || mainBannerPath.isBlank()) {
            mainBannerPath = (mainBannerUrl == null) ? "" : mainBannerUrl.trim();
        }

        String[] bannerUrls = req.getParameterValues("bannerUrls");

        // ✅ chỉ gọi 1 lần, không insert 2 lần
        promotionService.createPromotionWithProductsAndBanners(p, productIds, mainBannerPath, bannerUrls);
        System.out.println("bannerUrls=" + (bannerUrls == null ? "null" : bannerUrls.length));
        resp.sendRedirect(req.getContextPath() + "/admin/event/list");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        Promotion p = buildPromotionFromRequest(req, true);

        int[] productIds = parseProductIds(req);

        String mainBannerUrl = req.getParameter("mainBannerUrl");
        Part mainBannerFile = req.getPart("mainBannerFile");

        String mainBannerPath = saveBannerFile(mainBannerFile, req);
        if (mainBannerPath == null || mainBannerPath.isBlank()) {
            mainBannerPath = (mainBannerUrl == null) ? "" : mainBannerUrl.trim();
        }

        String[] bannerUrls = req.getParameterValues("bannerUrls");

        promotionService.updatePromotionWithProductsAndBanners(p, productIds, mainBannerPath, bannerUrls);

        resp.sendRedirect(req.getContextPath() + "/admin/event/list");
    }

    private Promotion buildPromotionFromRequest(HttpServletRequest req, boolean isUpdate) {
        Promotion p = new Promotion();

        if (isUpdate) {
            p.setId(Integer.parseInt(req.getParameter("id")));
            p.setStatus(req.getParameter("status"));
        } else {
            p.setStatus("ACTIVE");
        }

        p.setTitle(req.getParameter("title"));
        p.setContent(req.getParameter("content"));
        p.setStartDate(Date.valueOf(req.getParameter("startDate")));
        p.setEndDate(Date.valueOf(req.getParameter("endDate")));
        p.setDiscountType(req.getParameter("discountType"));

        String dv = req.getParameter("discountValue");
        if (dv == null || dv.trim().isEmpty()) dv = "0";
        p.setDiscountValue(Double.parseDouble(dv));

        return p;
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        promotionService.deletePromotion(id);
        resp.sendRedirect(req.getContextPath() + "/admin/event/list");
    }

    private String saveBannerFile(Part filePart, HttpServletRequest req) throws IOException {
        if (filePart == null || filePart.getSize() == 0) return null;

        String contentType = filePart.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) return null;

        String submitted = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        String ext = "";
        int dot = submitted.lastIndexOf('.');
        if (dot >= 0) ext = submitted.substring(dot).toLowerCase();
        if (!(ext.equals(".png") || ext.equals(".jpg") || ext.equals(".jpeg") || ext.equals(".webp"))) {
            ext = ".jpg";
        }

        String fileName = "banner_" + System.currentTimeMillis() + ext;

        String uploadDir = req.getServletContext().getRealPath("/Uploads/Banners");
        Files.createDirectories(Paths.get(uploadDir));

        Path savePath = Paths.get(uploadDir, fileName);
        try (var in = filePart.getInputStream()) {
            Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return "Uploads/Banners/" + fileName;
    }
}