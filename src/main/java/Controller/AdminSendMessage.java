package Controller;

import Model.DAO.MessageDAO;
import Model.Object.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AdminSendMessage", value = "/AdminSendMessage")
public class AdminSendMessage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User admin = (User) request.getSession().getAttribute("admin");

        if (admin == null) {
            response.setStatus(401);
            return;
        }

        int userId = Integer.parseInt(request.getParameter("userId"));
        String content = request.getParameter("content");

        new MessageDAO().sendMessage(admin.getId(), userId, content);
        response.setStatus(200);
    }
}