package Controller;

import Model.Object.User;
import Model.Service.MessageService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "SendMessage", value = "/SendMessage")
public class SendMessage extends HttpServlet {

    private final MessageService messageService = new MessageService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null || user.getRole() != 1) {
            response.setStatus(401);
            return;
        }

        String content = request.getParameter("content");
        int adminId = messageService.getAdminId();

        if (adminId == 0) {
            response.setStatus(500);
            return;
        }

        try {
            messageService.send(user.getId(), adminId, content);
            response.setStatus(200);
        } catch (IllegalArgumentException e) {
            response.setStatus(400);
        }
    }
}
