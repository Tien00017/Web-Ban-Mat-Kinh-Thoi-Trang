package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;

import Model.DAO.ProductDAO;
import Model.DAO.ProductImgDAO;
import Model.Object.Product;
import Model.Object.ProductImage;

@WebServlet( "/AdminAddProduct")
@MultipartConfig
public class AdminAddProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        /* ===== 1. Tạo Product core ===== */
        Product p = new Product();
        p.setProductName(req.getParameter("productName"));
        p.setPrice(Double.parseDouble(req.getParameter("price")));
        p.setStock(Integer.parseInt(req.getParameter("stock")));
        p.setBrand(req.getParameter("brand"));
        p.setCategoryId(Integer.parseInt(req.getParameter("categoryId")));
        p.setOrigin(req.getParameter("origin"));
        p.setGeneralDescription(req.getParameter("description"));
        p.setShippingInfo(req.getParameter("shippingInfo"));
        p.setGuaranteeDetails(req.getParameter("guarantee"));
        p.setSoldQuantity(0);
        p.setDeleted(false);

        ProductDAO productDAO = new ProductDAO();

        /* ===== 2. Insert product ===== */
        int productId = productDAO.insertAndReturnId(p);
        if (productId <= 0) {
            resp.sendRedirect(req.getContextPath() + "/admin/product?error=insert_failed");
            return;
        }

        /* ===== 3. Upload & insert image ===== */
        Part file = req.getPart("image");
        if (file != null && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("/Images");

            File dir = new File(uploadPath);
            if (!dir.exists()) dir.mkdirs();

            file.write(uploadPath + File.separator + fileName);

            ProductImage img = new ProductImage();
            img.setProductId(productId);
            img.setImageUrl(fileName);
            img.setMain(true);
            img.setType("main");

            new ProductImgDAO().insert(img);
        }

        /* ===== 4. Redirect ===== */
        resp.sendRedirect(req.getContextPath() + "/WEB-INF/Views/Admin/AdminAddProduct.jsp");
    }
}
