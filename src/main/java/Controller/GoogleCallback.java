package Controller;

import Model.DAO.UserDAO;
import Model.Object.User;
import Model.Utils.Google;
import Model.Utils.Http;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "GoogleCallback", value = "/GoogleCallback")
public class GoogleCallback extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null) {
            response.sendRedirect("Login");
            return;
        }

        String tokenResponse = Http.post(
                "https://oauth2.googleapis.com/token",
                "client_id=" + Google.getClientId()
                        + "&client_secret=" + Google.getClientSecret()
                        + "&code=" + code
                        + "&grant_type=authorization_code"
                        + "&redirect_uri=" + Google.getRedirectUri()
        );

        JsonObject tokenJson = JsonParser.parseString(tokenResponse).getAsJsonObject();
        String accessToken = tokenJson.get("access_token").getAsString();

        String userInfo = Http.get(
                "https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken
        );

        JsonObject userJson = JsonParser.parseString(userInfo).getAsJsonObject();
        String email = userJson.get("email").getAsString();
        String name = userJson.get("name").getAsString();

        User u = UserDAO.getUserByEmail(email);
        if (u == null) {
            u = new User();
            u.setEmail(email);
            u.setDisplayName(name);
            UserDAO.registerGoogle(u);
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", u);

        response.sendRedirect(request.getContextPath() + "/Home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}