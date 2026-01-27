package Controller;

import Model.DAO.MessageDAO;
import Model.Object.Message;
import Model.Object.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminMessages")
public class AdminMessages extends HttpServlet {

    private final MessageDAO messageDAO = new MessageDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User admin = (User) request.getSession().getAttribute("user");

        // admin role = 0
        if (admin == null || admin.getRole() != 0) {
            response.setStatus(401);
            return;
        }

        String userIdRaw = request.getParameter("userId");
        if (userIdRaw == null || userIdRaw.trim().isEmpty()) {
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write("");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdRaw.trim());
        } catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        List<Message> messages = messageDAO.getConversation(userId, admin.getId());

        response.setContentType("text/html; charset=UTF-8");
        StringBuilder sb = new StringBuilder();

        for (Message m : messages) {
            boolean me = (m.getSenderId() == admin.getId());

            sb.append("<div class='row ").append(me ? "me-row" : "").append("'>")
                    .append("<div class='msg ").append(me ? "me" : "").append("'>")
                    .append(escapeHtml(m.getContent()))
                    .append("</div></div>");
        }

        response.getWriter().write(sb.toString());
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
