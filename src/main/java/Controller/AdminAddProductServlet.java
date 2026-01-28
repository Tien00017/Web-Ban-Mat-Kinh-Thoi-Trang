package Controller.Admin;

import Model.Object.Product;
import Model.Service.AdminAddProductService;
import Model.Service.AdminProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

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
            req.setAttribute("product", p);
            req.setAttribute("mode", "edit");
        }
        // ===== ADD PRODUCT =====
        else {
            req.setAttribute("mode", "add");
        }

        req.getRequestDispatcher("/AdminAddProduct.jsp").forward(req, resp);
    }

    /* =========================
       XỬ LÝ SUBMIT (ADD / EDIT)
       ========================= */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        Product p = new Product();

        // ===== FIELD CHUNG =====
        p.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
        p.setProductName(req.getParameter("productName"));
        p.setBrand(req.getParameter("brand"));
        p.setPrice(Long.parseLong(req.getParameter("price")));
        p.setStock(Integer.parseInt(req.getParameter("stock")));
        p.setOrigin(req.getParameter("origin"));
        p.setGeneralDescription(req.getParameter("generalDescription"));
        p.setShippingInfo(req.getParameter("shippingInfo"));
        p.setGuaranteeDetails(req.getParameter("guaranteeDetails"));

        String idParam = req.getParameter("id");

        // ===== EDIT PRODUCT =====
        if (idParam != null && !idParam.isEmpty()) {
            p.setId(Integer.parseInt(idParam));

            // các field chỉ dùng khi EDIT
            p.setSoldQuantity(Integer.parseInt(req.getParameter("soldQuantity")));
            p.setDeleted(Boolean.parseBoolean(req.getParameter("deleted")));

            productService.updateProduct(p);
        }
        // ===== ADD PRODUCT =====
        else {
            // mặc định khi thêm mới
            p.setSoldQuantity(0);
            p.setDeleted(false);

            addService.addProduct(p);
        }

        // quay về trang quản lý sản phẩm
        resp.sendRedirect("AdminProduct");
    }
}
