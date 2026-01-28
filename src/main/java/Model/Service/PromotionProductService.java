package Model.Service;

import Model.DAO.PromotionProductDAO;
import Model.Object.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionProductService {

    private final PromotionProductDAO promotionProductDAO = new PromotionProductDAO();

    /**
     * Lấy danh sách product_id thuộc promotion
     */
    public List<Integer> getProductIdsByPromotionId(int promotionId) {
        return promotionProductDAO.getProductIdsByPromotionId(promotionId);
    }

    public Map<Integer, Double> getDiscountMap(List<Integer> productIds) {
        return promotionProductDAO.getDiscountMapByProductIds(productIds);
    }

    public Map<Integer, Double> getSalePriceMap(List<Product> products) {
        Map<Integer, Double> salePriceMap = new HashMap<>();

        List<Integer> ids = products.stream()
                .map(Product::getId)
                .toList();

        Map<Integer, Double> discountMap = getDiscountMap(ids);

        for (Product p : products) {
            if (discountMap.containsKey(p.getId())) {
                double percent = discountMap.get(p.getId());
                double salePrice = p.getPrice() * (100 - percent) / 100.0;
                salePriceMap.put(p.getId(), salePrice);
            }
        }
        return salePriceMap;
    }
}

