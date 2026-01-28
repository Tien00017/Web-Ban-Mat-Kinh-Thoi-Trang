package Controller;

import Model.DAO.CategoryDAO;
import Model.DAO.ProductImgDAO;
import Model.Object.Product;
import Model.Object.ProductImage;
import Model.Service.AdminAddProductService;
import Model.Service.AdminProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024*1024,
        maxFileSize = 5*1024*1024,
        maxRequestSize = 20*1024*1024
)
@WebServlet("/AdminAddProduct")
public class AdminAddProductServlet extends HttpServlet {

    private AdminAddProductService addService;
    private AdminProductService productService;

    @Override
    public void init() {
        addService = new AdminAddProductService();
        productService = new AdminProductService();
    }

    /* =========================
       HIỂN THỊ FORM (ADD / EDIT)
       ========================= */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");

        // ===== EDIT PRODUCT =====
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);

            Product p = productService.getProductById(id);
            CategoryDAO categoryDAO = new CategoryDAO();
            req.setAttribute("categories", categoryDAO.findAll());
            req.setAttribute("product", p);
            req.setAttribute("mode", "edit");
        }
        // ===== ADD PRODUCT =====
        else {
            req.setAttribute("mode", "add");
        }
        CategoryDAO categoryDAO = new CategoryDAO();
        req.setAttribute("categories", categoryDAO.findAll());

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminAddProduct.jsp").forward(req, resp);
    }

    /* =========================
       XỬ LÝ SUBMIT (ADD / EDIT)
       ========================= */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Product p = new Product();

        p.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
        p.setProductName(req.getParameter("productName"));
        p.setBrand(req.getParameter("brand"));
        p.setPrice(Double.parseDouble(req.getParameter("price")));  // ✅ double
        p.setStock(Integer.parseInt(req.getParameter("stock")));
        p.setOrigin(req.getParameter("origin"));
        p.setGeneralDescription(req.getParameter("generalDescription"));
        p.setShippingInfo(req.getParameter("shippingInfo"));
        p.setGuaranteeDetails(req.getParameter("guaranteeDetails"));

        String idParam = req.getParameter("id");

        ProductImgDAO imgDAO = new ProductImgDAO();

        // ===== EDIT PRODUCT =====
        if (idParam != null && !idParam.isEmpty()) {
            p.setId(Integer.parseInt(idParam));

            p.setSoldQuantity(Integer.parseInt(req.getParameter("soldQuantity")));
            p.setDeleted(Boolean.parseBoolean(req.getParameter("deleted")));

            productService.updateProduct(p);

            // update ảnh nếu admin nhập URL hoặc upload file mới
            handleProductImages(req, imgDAO, p.getId());

        } else {
            // ===== ADD PRODUCT =====
            p.setSoldQuantity(0);
            p.setDeleted(false);

            int newId = addService.addProduct(p);
            if (newId > 0) {
                handleProductImages(req, imgDAO, newId);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/AdminProduct");
    }
    private void handleProductImages(HttpServletRequest req, ProductImgDAO imgDAO, int productId)
            throws IOException, ServletException {

        // 1) URL ảnh chính (tùy chọn)
        String mainUrl = req.getParameter("mainImageUrl");
        if (mainUrl != null) mainUrl = mainUrl.trim();

        // 2) Upload file (tùy chọn, nhiều ảnh)
        List<Part> parts = req.getParts().stream()
                .filter(p -> "images".equals(p.getName()) && p.getSize() > 0)
                .toList();

        boolean insertedAny = false;

        // Nếu có upload file => ưu tiên file làm ảnh chính
        if (!parts.isEmpty()) {
            imgDAO.unsetMainImages(productId);

            boolean first = true;
            for (Part part : parts) {
                String savedPath = saveProductImageFile(part, req);
                if (savedPath == null) continue;

                ProductImage img = new ProductImage();
                img.setProductId(productId);
                img.setImageUrl(savedPath);      // đường dẫn tương đối
                img.setMain(first);              // ảnh đầu tiên là main
                img.setType("main");
                imgDAO.insert(img);

                first = false;
                insertedAny = true;
            }
        }

        // Nếu không upload file, nhưng có URL => insert URL làm main
        if (!insertedAny && mainUrl != null && !mainUrl.isEmpty()) {
            imgDAO.unsetMainImages(productId);

            ProductImage img = new ProductImage();
            img.setProductId(productId);
            img.setImageUrl(mainUrl);   // URL đầy đủ
            img.setMain(true);
            img.setType("main");
            imgDAO.insert(img);
        }
    }

    private String saveProductImageFile(Part filePart, HttpServletRequest req) throws IOException {
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

        String fileName = "p_" + System.currentTimeMillis() + ext;

        String uploadDir = req.getServletContext().getRealPath("/Uploads/Products");
        Files.createDirectories(Paths.get(uploadDir));

        Path savePath = Paths.get(uploadDir, fileName);
        try (var in = filePart.getInputStream()) {
            Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return "Uploads/Products/" + fileName; // đường dẫn tương đối lưu DB
    }

}

