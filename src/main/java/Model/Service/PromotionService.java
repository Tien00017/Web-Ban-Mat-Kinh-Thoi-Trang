
package Model.Service;

import Model.DAO.PromotionDAO;
import Model.Object.Promotion;

import java.util.List;


public class PromotionService {

    private final PromotionDAO promotionDAO = new PromotionDAO();


    public Promotion getById(int promotionId) {
        return promotionDAO.getById(promotionId);
    }
    public List<Promotion> getAllActive() {
        return promotionDAO.getAllActive();
    }

}
