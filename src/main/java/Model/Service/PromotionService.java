package Model.Service;

import Model.DAO.ProductDAO;
import Model.DAO.PromotionDAO;
import Model.Object.Product;
import Model.Object.Promotion;

import java.util.List;

public class PromotionService {

    private final PromotionDAO promotionDAO = new PromotionDAO();
    private final ProductDAO productDAO = new ProductDAO();

    public List<Promotion> getAllPromotions() {
        return promotionDAO.findAll();
    }

    public Promotion getPromotionById(int id) {
        return promotionDAO.findById(id);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public List<Integer> getSelectedProductIds(int promotionId) {
        return promotionDAO.getProductIdsByPromotionId(promotionId);
    }

    public void createPromotionWithProducts(Promotion p, int[] productIds) {
        // bạn đã có transaction trong DAO: insertWithProducts
        promotionDAO.insertWithProducts(p, productIds);
    }

    public void updatePromotionWithProducts(Promotion p, int[] productIds) {
        // bạn đã có transaction trong DAO: updateWithProducts
        promotionDAO.updateWithProducts(p, productIds);
    }

    public void deletePromotion(int id) {
        promotionDAO.delete(id);
    }
    public void createPromotionWithProductsAndBanners(Promotion p, int[] productIds, String mainBannerUrl, String[] bannerUrls) {
        int promotionId = promotionDAO.insertWithProducts(p, productIds);
        promotionDAO.replaceBanners(promotionId, mainBannerUrl, bannerUrls);
    }

    public void updatePromotionWithProductsAndBanners(Promotion p, int[] productIds, String mainBannerUrl, String[] bannerUrls) {
        promotionDAO.updateWithProducts(p, productIds);
        promotionDAO.replaceBanners(p.getId(), mainBannerUrl, bannerUrls);
    }

    public String getMainBannerUrl(int promotionId) {
        return promotionDAO.getMainBannerUrl(promotionId);
    }

    public List<String> getExtraBannerUrls(int promotionId) {
        return promotionDAO.getExtraBannerUrls(promotionId);
    }
}
