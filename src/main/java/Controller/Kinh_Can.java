package Controller;

import Model.DAO.ProductDAO;
import Model.DAO.ProductImgDAO;
import Model.Object.Product;
import Model.Object.ProductImage;
import Model.Service.ProductImgService;
import Model.Service.ProductService;
import Model.Service.PromotionProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "Kinh_Can", value = "/Kinh_Can")
public class Kinh_Can extends HttpServlet {

    private ProductService productService = new ProductService();
    private ProductImgService productImgService = new ProductImgService();
    private PromotionProductService promotionProductService = new PromotionProductService();

    @Override
    public void init() {
        productService = new ProductService();
        productImgService = new ProductImgService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = 1;

        // ===== 1. LẤY FILTER =====
        String material = request.getParameter("material");
        String shape = request.getParameter("shape");
        String priceRange = request.getParameter("priceRange");
        String sortPrice = request.getParameter("sortPrice");

        // ===== 2. PAGE =====
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            if (page < 1) page = 1;
        } catch (Exception ignored) {}

        int pageSize = 9;

        // ===== 3. LẤY DANH SÁCH ĐÃ LỌC =====
        List<Product> allProducts = productService.getFilteredProducts(
                categoryId,
                material,
                shape,
                null,
                priceRange,
                sortPrice
        );

        // ===== 4. PHÂN TRANG =====
        int totalProducts = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        List<Product> displayProducts =
                productService.paginate(allProducts, page, pageSize);

        // ===== 5. LẤY ẢNH CHÍNH =====
        List<Integer> productIds = displayProducts.stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        Map<Integer, ProductImage> imageMap =
                productImgService.getMainImages(productIds);

        Map<Integer, Double> salePriceMap =
                promotionProductService.getSalePriceMap(displayProducts);

        Map<Integer, Double> discountMap =
                promotionProductService.getDiscountMap(productIds);

        // ===== 6. ĐẨY RA JSP =====
        request.setAttribute("products", displayProducts);
        request.setAttribute("imageMap", imageMap);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("salePriceMap", salePriceMap);
        request.setAttribute("discountMap", discountMap);

        request.getRequestDispatcher("/WEB-INF/Views/Kinh_Can.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}