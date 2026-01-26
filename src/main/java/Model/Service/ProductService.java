package Model.Service;

import Model.DAO.ProductDAO;
import Model.Object.Product;

import java.util.List;

public class ProductService {

    private final ProductDAO productDAO = new ProductDAO();

    public Product getProductDetail(int productId) {
        Product p = productDAO.getProductById(productId);
        if (p == null) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }
        return p;
    }

    public List<Product> getSimilarProducts(int productId) {
        Product base = getProductDetail(productId);
        return productDAO.getSimilarProducts(base, 3);
    }

    public List<Product> getFilteredProducts(
            int categoryId,
            String material,
            String shape,
            String color,
            String priceRange,
            String sortPrice
    ) {
        return productDAO.getFilteredProducts(
                categoryId, material, shape, color, priceRange, sortPrice
        );
    }

    public List<Product> paginate(List<Product> products, int page, int pageSize) {
        int from = Math.max(0, (page - 1) * pageSize);
        int to = Math.min(from + pageSize, products.size());
        return products.subList(from, to);
    }
    public List<Product> search(String keyword) {
    return productDAO.search(keyword);
}
}

