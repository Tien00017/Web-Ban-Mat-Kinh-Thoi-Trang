package Controller;

import Model.Object.Product;
import Model.Service.AdminProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminProduct")
public class AdminProductServlet extends HttpServlet {

    private AdminProductService productService;

    @Override
    public void init() {
        productService = new AdminProductService();
    }

    // HIỂN THỊ DANH SÁCH
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        // XÓA SẢN PHẨM
        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            productService.deleteProduct(id);
            resp.sendRedirect("AdminProduct");
            return;
        }

        // HIỂN THỊ DANH SÁCH
        List<Product> list = productService.getAllProducts();
        req.setAttribute("products", list);
        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminProduct.jsp").forward(req, resp);
    }
}
