package Model.Service;

import Model.DAO.AdminAddProductDAO;
import Model.Object.Product;

public class AdminAddProductService {

    private final AdminAddProductDAO productDAO;

    public AdminAddProductService() {
        this.productDAO = new AdminAddProductDAO();
    }


    public int addProduct(Product p) {
        return productDAO.insertProduct(p);
    }
}
