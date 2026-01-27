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
//@WebServlet(name = "FakeLoginAdmin", value = "/FakeLoginAdmin")
//public class FakeLoginAdmin extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        User admin = new User();
//        admin.setId(2);          // ID ADMIN
//        admin.setEmail("admin@test.com");
//        admin.setRole(1);        // ADMIN
//
//        request.getSession().setAttribute("user", admin);
//        response.sendRedirect("AdminContact");
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}