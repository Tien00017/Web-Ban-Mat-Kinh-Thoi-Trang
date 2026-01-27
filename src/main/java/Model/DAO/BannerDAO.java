package Model.DAO;

import Model.Object.Banner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BannerDAO extends BaseDAO {

    public Banner getMainBanner(int promotionId) {

        String sql = """
            SELECT id, promotions_id, image_url, is_main, created_at
            FROM banners
            WHERE promotions_id = ? AND is_main = true
            ORDER BY created_at DESC
            LIMIT 1
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, promotionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Banner b = new Banner();
                b.setId(rs.getInt("id"));
                b.setPromotionId(rs.getInt("promotions_id"));
                b.setImageUrl(rs.getString("image_url"));
                b.setMain(rs.getBoolean("is_main"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                return b;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Banner> getAllBanner(int promotionId) {

        List<Banner> banners = new ArrayList<>();

        String sql = """
            SELECT id, promotions_id, image_url, is_main, created_at
            FROM banners
            WHERE promotions_id = ?
            ORDER BY is_main DESC, created_at DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, promotionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Banner b = new Banner();
                b.setId(rs.getInt("id"));
                b.setPromotionId(rs.getInt("promotions_id"));
                b.setImageUrl(rs.getString("image_url"));
                b.setMain(rs.getBoolean("is_main"));
                b.setCreatedAt(rs.getTimestamp("created_at"));
                banners.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return banners;
    }

    public List<Banner> getBannersByPromotionId(int promotionId) {
        return getAllBanner(promotionId);
    }
}
