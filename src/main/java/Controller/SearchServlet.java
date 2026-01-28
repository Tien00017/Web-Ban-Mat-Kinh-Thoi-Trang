package Controller;

import Model.Object.Product;
import Model.Object.ProductImage;
import Model.Service.ProductImgService;
import Model.Service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/Search")
public class SearchServlet extends HttpServlet {

    private ProductService productService = new ProductService();
    private ProductImgService productImgService = new ProductImgService();

    @Override
    public void init() {
        productService = new ProductService();
        productImgService = new ProductImgService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("keyword");

        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }

        List<Product> products = productService.search(keyword);

        List<Integer> productIds = products.stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        Map<Integer, ProductImage> productImages =
                productImgService.getMainImages(productIds);

        // 3️⃣ Truyền sang JSP
        request.setAttribute("keyword", keyword);
        request.setAttribute("products", products);
        request.setAttribute("productImages", productImages);

        request.getRequestDispatcher("/WEB-INF/Views/SearchResult.jsp")
                .forward(request, response);
    }
}
