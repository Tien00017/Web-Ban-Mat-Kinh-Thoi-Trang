package Controller;

import Model.DAO.UserDAO;
import Model.Object.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "ChangePassword", value = "/ChangePassword")
public class ChangePassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmPassword");

        if (!newPass.equals(confirm)) {
            response.sendRedirect("Profile?error=confirm");
            return;
        }

        UserDAO dao = new UserDAO();
        boolean ok = dao.changePassword(user.getId(), oldPass, newPass);

        if (!ok) {
            response.sendRedirect("Profile?error=oldpass");
        } else {
            response.sendRedirect("Profile?success=password");
        }
    }
}