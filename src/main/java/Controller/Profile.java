package Controller;

import Model.DAO.UserDAO;
import Model.Object.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "Profile", value = "/Profile")
public class Profile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect("Login");
            return;
        }

        UserDAO dao = new UserDAO();
        User freshUser = dao.getById(user.getId());

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
            case "logout":
                handleLogout(request, response);
                break;

            case "changePassword":
                handleChangePassword(request, response);
                break;
            case "updateInfo":
                handleUpdateInfo(request, response);
                break;
            // sau này thêm:
            // case "updateInfo":
            // case "uploadAvatar":

            default:
                response.sendRedirect(request.getContextPath() + "/Profile");
        }
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) {
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect(request.getContextPath() + "/Home");
    }
    private void handleUpdateInfo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        User sessionUser = (User) request.getSession().getAttribute("user");

        User u = new User();
        u.setId(sessionUser.getId());
        u.setFullName(request.getParameter("fullName"));
        u.setDisplayName(request.getParameter("fullName")); // hoặc tách riêng
        u.setPhone(request.getParameter("phone"));
        u.setAddress(request.getParameter("address"));
        u.setGender(Integer.parseInt(request.getParameter("gender")));

        String birth = request.getParameter("birthDate");
        if (birth != null && !birth.isEmpty()) {
            u.setBirthDate(java.sql.Date.valueOf(birth));
        }

        UserDAO dao = new UserDAO();
        dao.updateProfile(u);

        // reload lại user
        request.getSession().setAttribute("user", dao.getById(u.getId()));

        response.sendRedirect(request.getContextPath() + "/Profile");
    }

}