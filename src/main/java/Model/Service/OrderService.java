package Model.Service;

import Model.DAO.OrderDAO;
import Model.Object.Order;
import Model.Object.OrderAdminView;

import java.sql.SQLException;
import java.util.List;

public class OrderService {

    private final OrderDAO orderDAO = new OrderDAO();

    // ================= CREATE ORDER =================
    public int createOrder(int userId,
                           String name,
                           String phone,
                           String address,
                           String paymentMethod,
                           double totalAmount) {

        Order order = new Order();
        order.setUserId(userId);
        order.setName(name);
        order.setPhone(phone);
        order.setShippingAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING"); // mặc định

        try {
            return orderDAO.insertOrder(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // ================= ORDER HISTORY =================
    public List<Order> getOrdersByUserId(int userId) {
        try {
            return orderDAO.getOrdersByUser(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }
    public List<OrderAdminView> getAllAdminOrders() {
        return orderDAO.findAllAdminView();
    }

    public void updateOrderStatusByAction(int orderId, String action) {
        String newStatus = switch (action) {
            case "complete" -> "Hoàn tất";
            case "shipping" -> "Đang vận chuyển";
            case "cancel" -> "Đã hủy";
            default -> null;
        };

        if (newStatus == null) {
            throw new IllegalArgumentException("invalid_action");
        }

        boolean ok = orderDAO.updateStatus(orderId, newStatus);
        if (!ok) {
            throw new RuntimeException("update_fail");
        }
    }


}
