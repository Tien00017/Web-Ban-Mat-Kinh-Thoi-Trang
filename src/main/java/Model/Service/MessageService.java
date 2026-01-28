package Model.Service;

import Model.DAO.MessageDAO;
import Model.DAO.UserDAO;
import Model.Object.Message;
import Model.Object.User;

import java.util.List;

public class MessageService {

    private final MessageDAO messageDAO = new MessageDAO();
    private final UserDAO userDAO = new UserDAO();

    // ===== Common =====
    public int getAdminId() {
        return userDAO.getAdminId(); // admin role = 0
    }

    public User getUserById(int id) {
        return userDAO.getById(id);
    }

    public List<Message> getConversation(int userId, int adminId) {
        return messageDAO.getConversation(userId, adminId);
    }

    public void markRead(int senderId, int receiverId) {
        messageDAO.markMessagesAsRead(senderId, receiverId);
    }

    public void send(int senderId, int receiverId, String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("empty_content");
        }
        messageDAO.sendMessage(senderId, receiverId, content.trim());
    }

    // ===== Admin =====
    public List<User> getChatUsersForAdmin(int adminId) {
        List<Integer> userIds = messageDAO.getUserIdsChattedWithAdmin(adminId);
        return userDAO.getUsersByIds(userIds);
    }

    // ===== HTML fragment builders (để controller không đụng DAO) =====
    public String buildMessagesHtml(List<Message> messages, int meId) {
        StringBuilder sb = new StringBuilder();
        for (Message m : messages) {
            boolean me = (m.getSenderId() == meId);
            sb.append("<div class='row ").append(me ? "me-row" : "").append("'>")
                    .append("<div class='msg ").append(me ? "me" : "").append("'>")
                    .append(escapeHtml(m.getContent()))
                    .append("</div></div>");
        }
        return sb.toString();
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
