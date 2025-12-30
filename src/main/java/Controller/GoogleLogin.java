package Controller;

import Model.Utils.Google;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "GoogleLogin", value = "/GoogleLogin")
public class GoogleLogin  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String googleAuthUrl =
                "https://accounts.google.com/o/oauth2/v2/auth"
                        + "?client_id=" + Google.getClientId()
                        + "&redirect_uri=" + Google.getRedirectUri()
                        + "&response_type=code"
                        + "&scope=email%20profile"
                        + "&prompt=select_account";
        response.sendRedirect(googleAuthUrl);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}