package Controller;

import Model.Object.Message;
import Model.Object.User;
import Model.Service.MessageService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/ContactMessages")
public class ContactMessages extends HttpServlet {

    private final MessageService messageService = new MessageService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null || user.getRole() != 1) {
            response.setStatus(401);
            return;
        }

        int adminId = messageService.getAdminId();
        if (adminId == 0) {
            response.setStatus(500);
            response.getWriter().write("No admin found");
            return;
        }

        List<Message> messages = messageService.getConversation(user.getId(), adminId);

        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(messageService.buildMessagesHtml(messages, user.getId()));
    }
}
