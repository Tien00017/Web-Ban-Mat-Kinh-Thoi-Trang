package Model.DAO;

import Model.Object.News;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class NewsDAO extends BaseDAO {

    // Lấy tin mới (hiển thị trang chủ)
    public List<News> getLatestNews(int limit) {
        List<News> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM news
            WHERE deleted = false
            ORDER BY created_at DESC
            LIMIT ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapNews(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Search tin tức
    public List<News> searchByKeyword(String keyword) {
        List<News> list = new ArrayList<>();

        String sql = """
            SELECT *
            FROM news
            WHERE deleted = false
              AND (title LIKE ? OR content LIKE ?)
            ORDER BY created_at DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapNews(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Map ResultSet → News
    private News mapNews(ResultSet rs) throws Exception {
        News n = new News();
        n.setId(rs.getInt("id"));
        n.setTitle(rs.getString("title"));
        n.setContent(rs.getString("content"));
        n.setImage(rs.getString("image"));
        n.setCreatedAt(rs.getString("created_at"));
        n.setDeleted(rs.getBoolean("deleted"));
        return n;
    }
}
