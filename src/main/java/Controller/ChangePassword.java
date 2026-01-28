package Controller;

import Model.Object.User;
import Model.Service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "ChangePassword", value = "/ChangePassword")
public class ChangePassword extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        String oldPass = request.getParameter("oldPassword");
        String newPass = request.getParameter("newPassword");
        String confirm = request.getParameter("confirmPassword");

        try {
            boolean ok = userService.changePassword(user.getId(), oldPass, newPass, confirm);

            if (!ok) {
                response.sendRedirect("Profile?error=oldpass");
                return;
            }

            // refresh session user (optional)
            request.getSession().setAttribute("user", userService.getUserById(user.getId()));
            response.sendRedirect("Profile?success=password");

        } catch (IllegalArgumentException e) {
            // confirm / weak
            response.sendRedirect("Profile?error=" + e.getMessage());
        }
    }
}
