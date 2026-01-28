package Model.DAO;

import Model.Object.Promotion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO {

    // ===== ADMIN: lấy tất cả =====
    public List<Promotion> findAll() {
        List<Promotion> list = new ArrayList<>();

        String sql = """
        SELECT p.*,
               b.image_url AS main_banner_url
        FROM promotions p
        LEFT JOIN banners b
               ON b.promotions_id = p.id
              AND b.is_main = TRUE
        ORDER BY p.id DESC
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapResultSet(rs));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Promotion findById(int id) {
        String sql = "SELECT * FROM promotions WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int insert(Promotion p) {
        String sql = """
                    INSERT INTO promotions (title, content, start_date, end_date, discount_type, discount_value, status)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTitle());
            ps.setString(2, p.getContent());
            ps.setDate(3, p.getStartDate());
            ps.setDate(4, p.getEndDate());
            ps.setString(5, p.getDiscountType());
            ps.setDouble(6, p.getDiscountValue());
            ps.setString(7, p.getStatus());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void update(Promotion p) {
        String sql = """
                    UPDATE promotions
                    SET title=?, content=?, start_date=?, end_date=?,
                        discount_type=?, discount_value=?, status=?
                    WHERE id=?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTitle());
            ps.setString(2, p.getContent());
            ps.setDate(3, p.getStartDate());
            ps.setDate(4, p.getEndDate());
            ps.setString(5, p.getDiscountType());
            ps.setDouble(6, p.getDiscountValue());
            ps.setString(7, p.getStatus());
            ps.setInt(8, p.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM promotions WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Promotion mapResultSet(ResultSet rs) throws SQLException {
        Promotion p = new Promotion();
        p.setId(rs.getInt("id"));
        p.setTitle(rs.getString("title"));
        p.setContent(rs.getString("content"));
        p.setStartDate(rs.getDate("start_date"));
        p.setEndDate(rs.getDate("end_date"));
        p.setDiscountType(rs.getString("discount_type"));
        p.setDiscountValue(rs.getDouble("discount_value"));
        p.setStatus(rs.getString("status"));
        // banner chính (nếu có)
        try {
            p.setMainBannerUrl(rs.getString("main_banner_url"));
        } catch (SQLException ignored) {
            // trường hợp query không join banner (an toàn)
        }

        return p;
    }

    public void insertPromotionProduct(int promotionId, int productId) {
        String sql = "INSERT INTO promotion_product (promotion_id, product_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, promotionId);
            ps.setInt(2, productId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePromotionProducts(int promotionId) {
        String sql = "DELETE FROM promotion_product WHERE promotion_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, promotionId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertWithProducts(Promotion p, int[] productIds) {
        String insertPromotionSql = """
                    INSERT INTO promotions (title, content, start_date, end_date, discount_type, discount_value, status)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        String insertLinkSql = "INSERT INTO promotion_product (promotion_id, product_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int promotionId;

            try (PreparedStatement ps = conn.prepareStatement(insertPromotionSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, p.getTitle());
                ps.setString(2, p.getContent());
                ps.setDate(3, p.getStartDate());
                ps.setDate(4, p.getEndDate());
                ps.setString(5, p.getDiscountType());
                ps.setDouble(6, p.getDiscountValue());
                ps.setString(7, p.getStatus());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return -1;
                    }
                    promotionId = rs.getInt(1);
                }
            }

            if (productIds != null && productIds.length > 0) {
                try (PreparedStatement psLink = conn.prepareStatement(insertLinkSql)) {
                    for (int pid : productIds) {
                        psLink.setInt(1, promotionId);
                        psLink.setInt(2, pid);
                        psLink.addBatch();
                    }
                    psLink.executeBatch();
                }
            }

            conn.commit();
            return promotionId;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Integer> getProductIdsByPromotionId(int promotionId) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT product_id FROM promotion_product WHERE promotion_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, promotionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) ids.add(rs.getInt("product_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }

    public void updateWithProducts(Promotion p, int[] productIds) {
        String updatePromotionSql = """
                    UPDATE promotions
                    SET title=?, content=?, start_date=?, end_date=?,
                        discount_type=?, discount_value=?, status=?
                    WHERE id=?
                """;

        String deleteLinkSql = "DELETE FROM promotion_product WHERE promotion_id=?";
        String insertLinkSql = "INSERT INTO promotion_product (promotion_id, product_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(updatePromotionSql)) {
                ps.setString(1, p.getTitle());
                ps.setString(2, p.getContent());
                ps.setDate(3, p.getStartDate());
                ps.setDate(4, p.getEndDate());
                ps.setString(5, p.getDiscountType());
                ps.setDouble(6, p.getDiscountValue());
                ps.setString(7, p.getStatus());
                ps.setInt(8, p.getId());
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(deleteLinkSql)) {
                ps.setInt(1, p.getId());
                ps.executeUpdate();
            }

            if (productIds != null && productIds.length > 0) {
                try (PreparedStatement ps = conn.prepareStatement(insertLinkSql)) {
                    for (int pid : productIds) {
                        ps.setInt(1, p.getId());
                        ps.setInt(2, pid);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceBanners(int promotionId, String mainUrl, String[] extraUrls) {
        String deleteSql = "DELETE FROM banners WHERE promotions_id = ?";
        String insertSql = "INSERT INTO banners(promotions_id, image_url, is_main, created_at) VALUES (?, ?, ?, NOW())";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, promotionId);
                ps.executeUpdate();
            }

            // main banner
            if (mainUrl != null && !mainUrl.trim().isEmpty()) {
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    ps.setInt(1, promotionId);
                    ps.setString(2, mainUrl.trim());
                    ps.setBoolean(3, true);
                    ps.executeUpdate();
                }
            }

            // extra banners
            if (extraUrls != null) {
                try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                    for (String u : extraUrls) {
                        if (u == null) continue;
                        u = u.trim();
                        if (u.isEmpty()) continue;
                        ps.setInt(1, promotionId);
                        ps.setString(2, u);
                        ps.setBoolean(3, false);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMainBannerUrl(int promotionId) {
        String sql = "SELECT image_url FROM banners WHERE promotions_id=? AND is_main=true ORDER BY id DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, promotionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<String> getExtraBannerUrls(int promotionId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT image_url FROM banners WHERE promotions_id=? AND (is_main=false OR is_main IS NULL) ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, promotionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(rs.getString(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
