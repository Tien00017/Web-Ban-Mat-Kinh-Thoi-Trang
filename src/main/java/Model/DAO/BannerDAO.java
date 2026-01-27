package Model.DAO;

import Model.Object.Banner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BannerDAO extends BaseDAO {

    // Lấy banner theo promotion (banner chính đứng đầu)
    public List<Banner> getByPromotionId(int promotionId) {
        List<Banner> list = new ArrayList<>();

        String sql = """
            SELECT *
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
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
