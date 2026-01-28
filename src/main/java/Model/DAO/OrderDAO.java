package Model.DAO;

import Model.Object.Order;
import Model.Object.OrderAdminView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public int insertOrder(Order order) throws SQLException {
        String sql = """
                INSERT INTO orders
                (user_id, total_amount, status, name, phone,
                 payment_method, shipping_address, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, NOW())
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps =
                     conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());
            ps.setString(3, order.getStatus());
            ps.setString(4, order.getName());
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getPaymentMethod());
            ps.setString(7, order.getShippingAddress());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // order_id vừa tạo
            }
        }

        return -1;
    }

    public List<Order> getOrdersByUser(int userId) throws SQLException {
        List<Order> list = new ArrayList<>();

        String sql = """
                SELECT *
                FROM orders
                WHERE user_id = ?
                ORDER BY created_at DESC
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setName(rs.getString("name"));
                o.setPhone(rs.getString("phone"));
                o.setPaymentMethod(rs.getString("payment_method"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setCreatedAt(rs.getTimestamp("created_at"));

                list.add(o);
            }
        }

        return list;
    }
    //AdminOrder
    public List<OrderAdminView> findAllAdminView() {
        List<OrderAdminView> list = new ArrayList<>();

        String sql = """
        SELECT
            o.id,
            o.name AS customer_name,
            o.phone,
            o.created_at,
            o.status,
            o.total_amount,
            COALESCE(SUM(oi.quantity), 0) AS total_qty,
            GROUP_CONCAT(CONCAT(p.product_name, ' x', oi.quantity) SEPARATOR ', ') AS products_summary
        FROM orders o
        LEFT JOIN order_items oi ON oi.order_id = o.id
        LEFT JOIN products p ON p.id = oi.product_id
        GROUP BY o.id, o.name, o.phone, o.created_at, o.status, o.total_amount
        ORDER BY o.id DESC
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrderAdminView v = new OrderAdminView();
                v.setId(rs.getInt("id"));
                v.setCustomerName(rs.getString("customer_name"));
                v.setPhone(rs.getString("phone"));
                v.setCreatedAt(rs.getTimestamp("created_at"));
                v.setStatus(rs.getString("status"));
                v.setTotalAmount(rs.getDouble("total_amount"));
                v.setTotalQuantity(rs.getInt("total_qty"));
                v.setProductsSummary(rs.getString("products_summary"));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public boolean updateStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
