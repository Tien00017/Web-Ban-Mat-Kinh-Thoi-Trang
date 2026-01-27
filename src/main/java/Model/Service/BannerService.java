package Model.Service;

import Model.DAO.BannerDAO;
import Model.Object.Banner;

import java.util.List;

public class BannerService {

    private final BannerDAO bannerDAO = new BannerDAO();

    // Lấy banner chính của các sự kiện ACTIVE
    public List<Banner> getMainBanners() {
        return bannerDAO.getMainBanners();
    }
}
