package Controller;

import Model.DAO.ProductDAO;
import Model.Object.Product;
import Model.Object.ProductImage;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Gong_Kinh", value = "/Gong_Kinh")
public class Gong_Kinh extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = 4;

        // ===== 1. LẤY PARAMETER LỌC =====
        String material = request.getParameter("material");
        String shape = request.getParameter("shape");
        String priceRange = request.getParameter("priceRange");
        String sortPrice = request.getParameter("sortPrice");

        // ===== 2. LẤY TRANG HIỆN TẠI =====
        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int pageSize = 9;
        int offset = (page - 1) * pageSize;

        // ===== 3. LẤY DANH SÁCH SẢN PHẨM =====
        List<Product> products = ProductDAO.getFilteredProducts(categoryId, material, shape, null, priceRange, sortPrice);
        System.out.println("products: " + products.size());

        // ===== 4. LẤY TỔNG SỐ SẢN PHẨM =====
        int totalProducts = products.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // ===== 5. LẤY ẢNH CHÍNH =====
        List<Integer> ids = products.stream()
                .map(Product::getId)
                .toList();

        Map<Integer, ProductImage> imageMap = ProductDAO.getMainImagesByProductIds(ids);
        List<Product> displayProducts = ProductDAO.getProductsByPage(products, offset, pageSize);

        // ===== 6. ĐẨY DỮ LIỆU RA JSP =====
        request.setAttribute("products", displayProducts);
        request.setAttribute("imageMap", imageMap);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        System.out.println(page);
        System.out.println(totalPages);

        request.getRequestDispatcher("/WEB-INF/Views/Gong_Kinh.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}