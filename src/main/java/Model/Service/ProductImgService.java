package Model.Service;

import Model.DAO.ProductImgDAO;
import Model.Object.ProductImage;

import java.util.List;
import java.util.Map;

public class ProductImgService {

    private final ProductImgDAO imgDAO = new ProductImgDAO();

    public List<ProductImage> getProductImages(int productId) {
        return imgDAO.getAllProductImageById(productId);
    }

    public Map<Integer, ProductImage> getMainImages(List<Integer> productIds) {
        return imgDAO.getMainImagesByProductIds(productIds);
    }

}
