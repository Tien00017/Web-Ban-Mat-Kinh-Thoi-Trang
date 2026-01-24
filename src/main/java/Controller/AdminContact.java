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

@WebServlet(name = "AdminContact", value = "/AdminContact")
public class AdminContact extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User admin = (User) session.getAttribute("admin");

        if (admin == null || admin.getRole() != 1) {
            response.sendRedirect("Login");
            return;
        }

        MessageDAO dao = new MessageDAO();

        // danh sách user đã chat
        List<Integer> userIds = dao.getUserIdsChattedWithAdmin(admin.getId());
        request.setAttribute("userIds", userIds);

        // nếu chọn 1 user
        String userIdRaw = request.getParameter("userId");
        if (userIdRaw != null) {
            int userId = Integer.parseInt(userIdRaw);
            List<Message> messages = dao.getConversation(userId, admin.getId());
            request.setAttribute("messages", messages);
            request.setAttribute("currentUserId", userId);
        }
        request.getRequestDispatcher("/WEB-INF/Views/Admin/AdminContact.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}