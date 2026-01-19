package Controller;

import Model.DAO.MessageDAO;
import Model.Object.Message;
import Model.Object.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Contact", value = "/Contact")
public class Contact extends HttpServlet {
    // ID admin mặc định (role = 1)
    private static final int ADMIN_ID = 1;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        MessageDAO dao = new MessageDAO();
        List<Message> messages = dao.getConversation(user.getId(), ADMIN_ID);

        request.setAttribute("messages", messages);
        request.setAttribute("adminId", ADMIN_ID);
        request.getRequestDispatcher("/WEB-INF/Views/Contact.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}