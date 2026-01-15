package Model.Service;

import Model.DAO.OrderItemDAO;
import Model.DAO.ProductDAO;
import Model.DAO.ProductImgDAO;
import Model.Object.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItemService {
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();
    private final ProductImgDAO imgDAO = new ProductImgDAO();
    private final ProductDAO productDAO = new ProductDAO();

    // ================= INSERT ORDER ITEMS =================
    public void insertOrderItems(int orderId, Cart cart) {

        if (orderId <= 0) return;

        cart.getCartItems().values().forEach(item -> {
            OrderItem oi = new OrderItem();
            oi.setOrderId(orderId);
            oi.setProductId(item.getProductId());
            oi.setQuantity(item.getQuantity());
            oi.setPrice(item.getPrice());

            try {
                orderItemDAO.insertOrderItem(oi);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public Map<Integer, List<OrderItem>> getOrderItemsMap(List<Order> orders) {
        Map<Integer, List<OrderItem>> map = new HashMap<>();

        for (Order order : orders) {
            try {
                List<OrderItem> items =
                        orderItemDAO.getItemsByOrderId(order.getId());
                map.put(order.getId(), items);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public Map<Integer, Map<Product, String>> buildOrderItemForProductMap(
            Map<Integer, List<OrderItem>> orderItemsMap) {

        Map<Integer, Map<Product, String>> result = new HashMap<>();

        // 1. collect productIds
        List<Integer> productIds = orderItemsMap.values().stream()
                .flatMap(List::stream)
                .map(OrderItem::getProductId)
                .distinct()
                .toList();

        if (productIds.isEmpty()) return result;

        // 2. load image
        Map<Integer, ProductImage> imageMap =
                imgDAO.getMainImagesByProductIds(productIds);

        // 3. build map<OrderItemId, Map<Product, imageUrl>>
        for (List<OrderItem> items : orderItemsMap.values()) {
            for (OrderItem item : items) {

                Product product = productDAO.getProductById(item.getProductId());
                if (product == null) continue;

                ProductImage img = imageMap.get(item.getProductId());

                Map<Product, String> value = new HashMap<>();
                value.put(
                        product,
                        img != null ? img.getImageUrl() : null
                );

                result.put(item.getId(), value);
            }
        }

        return result;
    }
}
