package Model.Service;

import Model.DAO.PromotionProductDAO;

import java.util.List;

public class PromotionProductService {

    private final PromotionProductDAO promotionProductDAO = new PromotionProductDAO();

    /**
     * Lấy danh sách product_id thuộc promotion
     */
    public List<Integer> getProductIdsByPromotionId(int promotionId) {
        return promotionProductDAO.getProductIdsByPromotionId(promotionId);
    }
}
