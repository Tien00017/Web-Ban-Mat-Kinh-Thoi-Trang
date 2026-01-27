package Model.DAO;

import Model.Object.Banner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BannerDAO extends BaseDAO {

    // Lấy banner chính của các sự kiện ACTIVE
    public List<Banner> getMainBanners() {
        List<Banner> list = new ArrayList<>();

        String sql = """
            SELECT b.*
            FROM banners b
            JOIN promotions p ON b.promotions_id = p.id
            WHERE b.is_main = true
              AND p.status = 'ACTIVE'
            ORDER BY b.created_at DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Banner b = new Banner();
                b.setId(rs.getInt("id"));
                b.setPromotionId(rs.getInt("promotions_id"));
                b.setImageUrl(rs.getString("image_url"));
                b.setMain(rs.getBoolean("is_main"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
