package Model.Service;

import Model.DAO.PromotionProductDAO;

import java.util.List;

public class PromotionProductService {

    private final PromotionProductDAO promotionProductDAO =
            new PromotionProductDAO();

    // Lấy danh sách product_id thuộc 1 sự kiện
    public List<Integer> getProductIdsByPromotion(int promotionId) {
        return promotionProductDAO.getProductIdsByPromotion(promotionId);
    }
}
