package Controller;

import Model.Object.User;
import Model.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "UpdateProfile", value = "/UpdateProfile")
public class UpdateProfile extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        user.setFullName(request.getParameter("fullName"));
        user.setDisplayName(request.getParameter("displayName"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));

        String genderRaw = request.getParameter("gender");
        if (genderRaw != null && !genderRaw.trim().isEmpty()) {
            user.setGender(Integer.parseInt(genderRaw.trim()));
        }

        String birth = request.getParameter("birthDate");
        if (birth != null && !birth.isEmpty()) {
            user.setBirthDate(java.sql.Date.valueOf(birth));
        } else {
            user.setBirthDate(null);
        }

        // ✅ gọi service
        userService.updateProfile(user);

        // refresh session bằng user mới nhất
        request.getSession().setAttribute("user", userService.getUserById(user.getId()));
        response.sendRedirect("Profile");
    }
}
