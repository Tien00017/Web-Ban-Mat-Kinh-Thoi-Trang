package Controller;

import Model.Object.Message;
import Model.Object.User;
import Model.Service.MessageService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Contact", value = "/Contact")
public class Contact extends HttpServlet {

    private final MessageService messageService = new MessageService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");

        // user role = 1
        if (user == null || user.getRole() != 1) {
            response.sendRedirect("Login");
            return;
        }

        int adminId = messageService.getAdminId();
        List<Message> messages = messageService.getConversation(user.getId(), adminId);

        request.setAttribute("messages", messages);
        request.setAttribute("adminId", adminId);

        request.getRequestDispatcher("/WEB-INF/Views/Contact.jsp")
                .forward(request, response);
    }
}
