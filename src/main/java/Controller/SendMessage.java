package Controller;

import Model.DAO.MessageDAO;
import Model.DAO.UserDAO;
import Model.Object.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "SendMessage", value = "/SendMessage")
public class SendMessage extends HttpServlet {

    private final MessageDAO dao = new MessageDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");

        // user role = 1
        if (user == null || user.getRole() != 1) {
            response.setStatus(401);
            return;
        }

        String content = request.getParameter("content");
        if (content == null || content.trim().isEmpty()) {
            response.setStatus(400);
            return;
        }

        int adminId = userDAO.getAdminId();
        if (adminId == 0) {
            response.setStatus(500);
            return;
        }

        dao.sendMessage(user.getId(), adminId, content.trim());
        response.setStatus(200);
    }
}
