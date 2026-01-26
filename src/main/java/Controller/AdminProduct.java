package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import Model.DAO.ProductDAO;
import Model.Object.Product;


@WebServlet("/admin/product")
public class AdminProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAO();
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/product");
            return;
        }

        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product p = dao.getProductById(id);
            req.setAttribute("product", p);
            req.getRequestDispatcher("/AdminEditProduct.jsp").forward(req, resp);
            return;
        }

        List<Product> list = dao.getAllProductsAdmin();
        req.setAttribute("products", list);
        req.getRequestDispatcher("/AdminProduct.jsp").forward(req, resp);
    }
}
