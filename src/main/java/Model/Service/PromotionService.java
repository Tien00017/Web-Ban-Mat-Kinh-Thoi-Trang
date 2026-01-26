package Model.Service;

import Model.DAO.PromotionDAO;
import Model.Object.Promotion;

import java.util.List;

public class PromotionService {

    private final PromotionDAO promotionDAO = new PromotionDAO();

    // Trang chủ: load khuyến mãi mới / đang active
    public List<Promotion> getLatestPromotions() {
        return promotionDAO.getLatestPromotions(5);
    }

    // Search theo tiêu đề / nội dung
//    public List<Promotion> search(String keyword) {
//        return promotionDAO.searchByKeyword(keyword);
//    }
    //Thanh tìm kiếm trên headere mà sao lại viết trong promotion viết lại nha
}
