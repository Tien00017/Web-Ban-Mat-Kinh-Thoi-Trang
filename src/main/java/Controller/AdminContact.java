package Controller;

import Model.Object.Message;
import Model.Object.User;
import Model.Service.MessageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminContact", value = "/AdminContact")
public class AdminContact extends HttpServlet {

    private final MessageService messageService = new MessageService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User admin = (User) request.getSession().getAttribute("user");

        // admin role = 0
        if (admin == null || admin.getRole() != 0) {
            response.sendRedirect("Login");
            return;
        }

        List<User> users = messageService.getChatUsersForAdmin(admin.getId());
        request.setAttribute("users", users);

        int currentUserId = 0;
        String userIdRaw = request.getParameter("userId");

        if (userIdRaw != null && !userIdRaw.trim().isEmpty()) {
            try { currentUserId = Integer.parseInt(userIdRaw.trim()); }
            catch (NumberFormatException ignored) { currentUserId = 0; }
        } else if (!users.isEmpty()) {
            currentUserId = users.get(0).getId();
        }

        if (currentUserId != 0) {
            List<Message> messages = messageService.getConversation(currentUserId, admin.getId());
            messageService.markRead(currentUserId, admin.getId());

            request.setAttribute("messages", messages);
            request.setAttribute("currentUserId", currentUserId);
            request.setAttribute("currentUser", messageService.getUserById(currentUserId));
        }

        request.getRequestDispatcher("/WEB-INF/Views/Admin/AdminContact.jsp")
                .forward(request, response);
    }
}
