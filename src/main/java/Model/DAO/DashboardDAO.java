package Model.DAO;

import Model.Object.AdminOrderRow;
import Model.Object.AdminProductRow;

import java.sql.*;
import java.util.*;

public class DashboardDAO {

    // Doanh thu: mình lấy tổng đơn "Hoàn tất" (bạn có thể đổi rule)
    public double getRevenueTotal() {
        String sql = "SELECT COALESCE(SUM(total_amount),0) FROM orders WHERE status = 'Hoàn tất'";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public int getTotalOrders() {
        String sql = "SELECT COUNT(*) FROM orders";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // Khách hàng mới 30 ngày (role=1 là user)
    public int getNewCustomers30d() {
        String sql = """
            SELECT COUNT(*)
            FROM users
            WHERE role = 1 AND created_at >= (NOW() - INTERVAL 30 DAY)
        """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // Đơn mới (limit 8)
    public List<AdminOrderRow> getRecentOrders(int limit) {
        List<AdminOrderRow> list = new ArrayList<>();
        String sql = """
            SELECT 
                o.id, o.name, o.phone, o.status, o.total_amount, o.created_at,
                COALESCE(SUM(oi.quantity),0) AS total_qty,
                GROUP_CONCAT(CONCAT(p.product_name,' x', oi.quantity) SEPARATOR ', ') AS products_summary
            FROM orders o
            LEFT JOIN order_items oi ON oi.order_id = o.id
            LEFT JOIN products p ON p.id = oi.product_id
            GROUP BY o.id, o.name, o.phone, o.status, o.total_amount, o.created_at
            ORDER BY o.created_at DESC
            LIMIT ?
        """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AdminOrderRow r = new AdminOrderRow();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setPhone(rs.getString("phone"));
                r.setStatus(rs.getString("status"));
                r.setTotalAmount(rs.getDouble("total_amount"));
                r.setCreatedAt(rs.getTimestamp("created_at"));
                r.setTotalQty(rs.getInt("total_qty"));
                r.setProductsSummary(rs.getString("products_summary"));
                list.add(r);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Sản phẩm mới (limit 6)
    public List<AdminProductRow> getRecentProducts(int limit) {
        List<AdminProductRow> list = new ArrayList<>();

        String sql = """
                    SELECT 
                      p.id, p.product_name, p.category_id, p.price, p.stock, p.deleted, p.created_at,
                      pi.image_url
                    FROM products p
                    LEFT JOIN product_images pi
                      ON pi.product_id = p.id
                     AND pi.is_main = true
                     AND pi.image_url NOT LIKE 'data:image/svg+xml%'
                    WHERE p.deleted = false
                    ORDER BY p.created_at DESC
                    LIMIT ?
                """;


        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AdminProductRow p = new AdminProductRow();
                p.setId(rs.getInt("id"));
                p.setProductName(rs.getString("product_name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setDeleted(rs.getBoolean("deleted"));
                p.setCreatedAt(rs.getTimestamp("created_at"));
                p.setImageUrl(rs.getString("image_url"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Doanh thu 7 ngày gần nhất (theo đơn hoàn tất)
    public Map<String, Double> getRevenueLast7Days() {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = """
            SELECT DATE(created_at) as d, COALESCE(SUM(total_amount),0) as rev
            FROM orders
            WHERE status='Hoàn tất' AND created_at >= (CURDATE() - INTERVAL 6 DAY)
            GROUP BY DATE(created_at)
            ORDER BY d ASC
        """;
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("d"), rs.getDouble("rev"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return map;
    }
    public java.util.Map<String, Integer> getOrderStatusCounts() {
        java.util.Map<String, Integer> map = new java.util.LinkedHashMap<>();

        // đảm bảo luôn có đủ 4 trạng thái
        map.put("Chờ xử lý", 0);
        map.put("Đang vận chuyển", 0);
        map.put("Hoàn tất", 0);
        map.put("Đã hủy", 0);

        String sql = """
        SELECT status, COUNT(*) c
        FROM orders
        GROUP BY status
    """;

        try (java.sql.Connection c = DBConnection.getConnection();
             java.sql.PreparedStatement ps = c.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String st = rs.getString("status");
                int count = rs.getInt("c");
                if (map.containsKey(st)) map.put(st, count);
            }
        } catch (Exception e) { e.printStackTrace(); }

        return map;
    }
}
