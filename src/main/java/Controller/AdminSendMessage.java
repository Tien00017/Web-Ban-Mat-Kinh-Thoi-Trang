package Controller;

import Model.DAO.MessageDAO;
import Model.Object.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminSendMessage", value = "/AdminSendMessage")
public class AdminSendMessage extends HttpServlet {

    private final MessageDAO dao = new MessageDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User admin = (User) request.getSession().getAttribute("user");

        // admin role = 0
        if (admin == null || admin.getRole() != 0) {
            response.setStatus(401);
            return;
        }

        String userIdRaw = request.getParameter("userId");
        String content = request.getParameter("content");

        if (userIdRaw == null || userIdRaw.trim().isEmpty() || content == null || content.trim().isEmpty()) {
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

        dao.sendMessage(admin.getId(), userId, content.trim());
        response.setStatus(200);
    }
}
