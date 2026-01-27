//package Controller;
//
//import Model.Object.User;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@WebServlet(name = "FakeLoginUser", value = "/FakeLoginUser")
//public class FakeLoginUser extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        User u = new User();
//        u.setId(1);               // ID USER trong DB
//        u.setEmail("user@test.com");
//        u.setRole(0);             // USER
//
//        request.getSession().setAttribute("user", u);
//        response.sendRedirect("Home");
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}