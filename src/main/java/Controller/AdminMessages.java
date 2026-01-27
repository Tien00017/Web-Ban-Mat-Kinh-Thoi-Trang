package Controller;

import Model.Object.Message;
import Model.Object.User;
import Model.Service.MessageService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminMessages")
public class AdminMessages extends HttpServlet {

    private final MessageService messageService = new MessageService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User admin = (User) request.getSession().getAttribute("user");

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

        List<Message> messages = messageService.getConversation(userId, admin.getId());
        // optional: mở chat body => đọc luôn
        messageService.markRead(userId, admin.getId());

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(messageService.buildMessagesHtml(messages, admin.getId()));
    }
}
