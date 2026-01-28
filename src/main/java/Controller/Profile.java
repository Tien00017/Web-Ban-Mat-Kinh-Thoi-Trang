package Controller;

import Model.Object.User;
import Model.Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "Profile", value = "/Profile")
public class Profile extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        User freshUser = userService.getUserById(user.getId());
        request.getSession().setAttribute("user", freshUser);

        request.getRequestDispatcher("/WEB-INF/Views/Profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/Profile");
            return;
        }

        switch (action) {
            case "logout" -> handleLogout(request, response);
            case "changePassword" -> handleChangePassword(request, response);
            case "updateInfo" -> handleUpdateInfo(request, response);
            default -> response.sendRedirect(request.getContextPath() + "/Profile");
        }
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) {
        // TODO: sau này đưa vào UserService.changePassword(...)
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        response.sendRedirect(request.getContextPath() + "/Home");
    }

    private void handleUpdateInfo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User sessionUser = (User) request.getSession().getAttribute("user");
        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        User u = new User();
        u.setId(sessionUser.getId());
        u.setFullName(request.getParameter("fullName"));
        u.setDisplayName(request.getParameter("displayName"));
        u.setPhone(request.getParameter("phone"));
        u.setAddress(request.getParameter("address"));

        String genderRaw = request.getParameter("gender");
        if (genderRaw != null && !genderRaw.trim().isEmpty()) {
            u.setGender(Integer.parseInt(genderRaw.trim()));
        }

        String birth = request.getParameter("birthDate");
        if (birth != null && !birth.isEmpty()) {
            u.setBirthDate(java.sql.Date.valueOf(birth));
        } else {
            u.setBirthDate(null);
        }

        // ✅ gọi service
        userService.updateProfile(u);

        request.getSession().setAttribute("user", userService.getUserById(u.getId()));
        response.sendRedirect(request.getContextPath() + "/Profile");
    }
}
