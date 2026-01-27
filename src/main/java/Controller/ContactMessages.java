package Controller;

import Model.DAO.MessageDAO;
import Model.DAO.UserDAO;
import Model.Object.Message;
import Model.Object.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ContactMessages")
public class ContactMessages extends HttpServlet {

    private final MessageDAO messageDAO = new MessageDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        // user role = 1
        if (user == null || user.getRole() != 1) {
            response.setStatus(401);
            return;
        }

        int adminId = userDAO.getAdminId(); // cần có method này trong UserDAO (mình gửi ở cuối)
        if (adminId == 0) {
            response.setStatus(500);
            response.getWriter().write("No admin found");
            return;
        }

        List<Message> messages = messageDAO.getConversation(user.getId(), adminId);

        response.setContentType("text/html; charset=UTF-8");
        StringBuilder sb = new StringBuilder();

        for (Message m : messages) {
            boolean me = (m.getSenderId() == user.getId());

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
