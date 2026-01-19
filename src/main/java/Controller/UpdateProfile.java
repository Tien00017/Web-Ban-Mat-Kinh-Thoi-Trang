package Controller;

import Model.DAO.UserDAO;
import Model.Object.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "UpdateProfile", value = "/UpdateProfile")
public class UpdateProfile extends HttpServlet {
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

        user.setFullName(request.getParameter("fullName"));
        user.setDisplayName(request.getParameter("displayName"));
        user.setPhone(request.getParameter("phone"));
        user.setAddress(request.getParameter("address"));

        // gender
        user.setGender(Integer.parseInt(request.getParameter("gender")));

        // birthDate
        String birth = request.getParameter("birthDate");
        if (birth != null && !birth.isEmpty()) {
            user.setBirthDate(java.sql.Date.valueOf(birth));
        }

        UserDAO dao = new UserDAO();
        dao.updateProfile(user);

        // refresh session
        request.getSession().setAttribute("user", dao.getById(user.getId()));
        response.sendRedirect("Profile");
    }
}