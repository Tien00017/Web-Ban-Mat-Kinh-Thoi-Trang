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

    public Promotion findById(int promotionId) {
        return promotionDAO.getById(promotionId);
    }
    public List<Promotion> getAllActive() {
        return promotionDAO.getAllActive();
    }

    public String getMainBannerUrl(int id) {
        return  promotionDAO.getMainBannerUrl(id);
    }

    public List<String> getExtraBannerUrls(int id) {
        return promotionDAO.getExtraBannerUrls(id);
    }

    public void createPromotionWithProductsAndBanners(Promotion p, int[] productIds, String mainBannerPath, String[] bannerUrls) {
        int promotionId = promotionDAO.insertWithProducts(p, productIds);
        promotionDAO.replaceBanners(promotionId, mainBannerPath, bannerUrls);
    }

    public void updatePromotionWithProductsAndBanners(Promotion p, int[] productIds, String mainBannerPath, String[] bannerUrls) {
        promotionDAO.updateWithProducts(p, productIds);
        promotionDAO.replaceBanners(p.getId(), mainBannerPath, bannerUrls);
    }
}