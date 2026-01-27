package Controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Home", value = "/Home")
public class Home extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BannerService bannerService = new BannerService();
        request.setAttribute("banners", bannerService.getMainBanners());
        request.getRequestDispatcher("/WEB-INF/Views/HomePage.jsp").forward(request, response);
    
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
