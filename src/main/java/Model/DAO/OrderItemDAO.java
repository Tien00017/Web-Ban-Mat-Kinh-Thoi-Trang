package Model.DAO;

import Model.Object.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    // Thêm 1 item vào order
    public void insertOrderItem(OrderItem item) throws SQLException {
        String sql = """
                INSERT INTO order_items
                (product_id, order_id, quantity, price)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getProductId());
            ps.setInt(2, item.getOrderId());
            ps.setInt(3, item.getQuantity());
            ps.setDouble(4, item.getPrice());

            ps.executeUpdate();
        }
    }

    // Lấy danh sách item theo order_id
    public List<OrderItem> getItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> list = new ArrayList<>();

        String sql = """
                SELECT *
                FROM order_items
                WHERE order_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setProductId(rs.getInt("product_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));

                list.add(item);
            }
        }

        return list;
    }
}
