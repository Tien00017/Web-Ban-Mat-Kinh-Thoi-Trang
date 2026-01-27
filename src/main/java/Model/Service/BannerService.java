
package Model.Service;

import Model.DAO.BannerDAO;
import Model.Object.Banner;

import java.util.List;

public class BannerService {

    private final BannerDAO bannerDAO = new BannerDAO();

    public Banner getMainBanner(int promotionId) {
        return bannerDAO.getMainBanner(promotionId);
    }

    public List<Banner> getAllBanner(int promotionId) {
        return bannerDAO.getAllBanner(promotionId);
    }
    public List<Banner> getBannerByPromotionId(int promotionId) {
        return bannerDAO.getBannersByPromotionId(promotionId);
    }
}
