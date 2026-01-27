package Controller;

import Model.DAO.MessageDAO;
import Model.DAO.UserDAO;
import Model.Object.Message;
import Model.Object.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminContact", value = "/AdminContact")
public class AdminContact extends HttpServlet {

    private final MessageDAO messageDAO = new MessageDAO();
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("user");

        if (admin == null || admin.getRole() != 0) { // admin role 0
            response.sendRedirect("Login");
            return;
        }

        List<Integer> userIds = messageDAO.getUserIdsChattedWithAdmin(admin.getId());
        List<User> users = userDAO.getUsersByIds(userIds);

        request.setAttribute("users", users);

        int currentUserId = 0;
        String userIdRaw = request.getParameter("userId");

        if (userIdRaw != null && !userIdRaw.trim().isEmpty()) {
            currentUserId = Integer.parseInt(userIdRaw.trim());
        } else if (!users.isEmpty()) {
            currentUserId = users.get(0).getId(); // ✅ lấy id user đầu tiên
        }    else if (!userIds.isEmpty()) {
            currentUserId = userIds.get(0);
        }

        if (currentUserId != 0) {
            List<Message> messages = messageDAO.getConversation(currentUserId, admin.getId());
            // đánh dấu tin nhắn user -> admin là đã đọc
            messageDAO.markMessagesAsRead(currentUserId, admin.getId());

            request.setAttribute("messages", messages);
            request.setAttribute("currentUserId", currentUserId);

            // info user để show header
            User u = userDAO.getById(currentUserId);
            request.setAttribute("currentUser", u);
        }

        request.getRequestDispatcher("/WEB-INF/Views/Admin/AdminContact.jsp")
                .forward(request, response);
    }
}
