package Model.Service;

import Model.DAO.AdminProductDAO;
import Model.Object.Product;

import java.util.List;

public class AdminProductService {

    private final AdminProductDAO productDAO;

    public AdminProductService() {
        this.productDAO = new AdminProductDAO();
    }

    /* =========================
       DANH SÁCH SẢN PHẨM
       ========================= */
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    /* =========================
       LẤY SẢN PHẨM THEO ID (EDIT)
       ========================= */
    public Product getProductById(int id) {
        return productDAO.getProductById(id);
    }

    /* =========================
       CẬP NHẬT SẢN PHẨM
       ========================= */
    public void updateProduct(Product p) {

        // không cho số âm
        if (p.getStock() < 0) {
            p.setStock(0);
        }
        if (p.getSoldQuantity() < 0) {
            p.setSoldQuantity(0);
        }

        productDAO.updateProduct(p);
    }

    /* =========================
       XÓA SẢN PHẨM (XÓA MỀM)
       ========================= */
    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }
}

