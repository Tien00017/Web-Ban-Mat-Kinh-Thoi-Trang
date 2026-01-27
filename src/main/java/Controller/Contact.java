package Controller;

import Model.DAO.MessageDAO;
import Model.DAO.UserDAO;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != 1) { //  user role 1
            response.sendRedirect("Login");
            return;
        }

        MessageDAO dao = new MessageDAO();
        int adminId = new UserDAO().getAdminId();
        List<Message> messages = dao.getConversation(user.getId(), adminId);

        request.setAttribute("messages", messages);
        request.setAttribute("adminId", adminId);
        request.getRequestDispatcher("/WEB-INF/Views/Contact.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}