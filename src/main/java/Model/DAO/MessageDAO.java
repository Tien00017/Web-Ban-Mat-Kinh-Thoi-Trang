package Model.DAO;

import Model.Object.Message;
import Model.Utils.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /* =========================
       1. GỬI TIN NHẮN
       ========================= */
    public void sendMessage(int senderId, int receiverId, String content) {
        String sql = """
            INSERT INTO messages (sender_id, receiver_id, content, sent_at, status)
            VALUES (?, ?, ?, NOW(), 'SENT')
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, content);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =========================
       2. LẤY TOÀN BỘ HỘI THOẠI
       (USER <-> ADMIN)
       ========================= */
    public List<Message> getConversation(int userId, int adminId) {
        List<Message> list = new ArrayList<>();

        String sql = """
        SELECT * FROM messages
        WHERE (sender_id = ? AND receiver_id = ?)
           OR (sender_id = ? AND receiver_id = ?)
        ORDER BY sent_at ASC
    """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, adminId);
            ps.setInt(3, adminId);
            ps.setInt(4, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message m = new Message();
                m.setId(rs.getInt("id"));
                m.setSenderId(rs.getInt("sender_id"));
                m.setReceiverId(rs.getInt("receiver_id"));
                m.setContent(rs.getString("content"));
                m.setSentAt(rs.getTimestamp("sent_at"));
                m.setStatus(rs.getString("status"));
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /* =========================
       3. ADMIN: LẤY DANH SÁCH USER ĐÃ CHAT
       ========================= */
    public List<Integer> getUserIdsChattedWithAdmin(int adminId) {
        List<Integer> list = new ArrayList<>();

        String sql = """
            SELECT DISTINCT sender_id
            FROM messages
            WHERE receiver_id = ?
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, adminId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("sender_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /* =========================
       4. ADMIN: LẤY HỘI THOẠI THEO USER
       ========================= */
    public List<Message> getConversationByUser(int adminId, int userId) {
        return getConversation(userId, adminId);
    }

    /* =========================
       5. ĐÁNH DẤU TIN NHẮN ĐÃ ĐỌC
       ========================= */
    public void markMessagesAsRead(int senderId, int receiverId) {
        String sql = """
            UPDATE messages
            SET status = 'READ'
            WHERE sender_id = ? AND receiver_id = ?
        """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
