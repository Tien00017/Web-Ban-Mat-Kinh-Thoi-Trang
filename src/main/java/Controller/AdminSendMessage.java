package Controller;

import Model.Object.User;
import Model.Service.MessageService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "AdminSendMessage", value = "/AdminSendMessage")
public class AdminSendMessage extends HttpServlet {

    private final MessageService messageService = new MessageService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User admin = (User) request.getSession().getAttribute("user");

        if (admin == null || admin.getRole() != 0) {
            response.setStatus(401);
            return;
        }

        String userIdRaw = request.getParameter("userId");
        String content = request.getParameter("content");

        if (userIdRaw == null || userIdRaw.trim().isEmpty()) {
            response.setStatus(400);
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdRaw.trim());
        } catch (NumberFormatException e) {
            response.setStatus(400);
            return;
        }

        try {
            messageService.send(admin.getId(), userId, content);
            response.setStatus(200);
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
        }
    }
}
