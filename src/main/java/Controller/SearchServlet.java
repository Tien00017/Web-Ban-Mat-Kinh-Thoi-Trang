package Controller;

import Model.Object.Product;
import Model.Service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/Search")
public class SearchServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

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

        request.setAttribute("keyword", keyword);
        request.setAttribute("products", products);

        request.getRequestDispatcher("/WEB-INF/Views/SearchResult.jsp")
               .forward(request, response);
    }
}
