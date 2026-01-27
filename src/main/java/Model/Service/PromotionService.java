
package Model.Service;

import Model.DAO.PromotionDAO;
import Model.Object.Promotion;


public class PromotionService {

    private final PromotionDAO promotionDAO = new PromotionDAO();


    public Promotion getById(int promotionId) {
        return promotionDAO.getById(promotionId);
    }
}
