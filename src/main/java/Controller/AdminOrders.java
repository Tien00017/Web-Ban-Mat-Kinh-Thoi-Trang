//package Controller;
//
//import Model.DAO.OrderDAO;
//import Model.Object.OrderAdminView;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/AdminOrders")
//public class AdminOrders extends HttpServlet {
//
//    private final OrderDAO orderDAO = new OrderDAO();
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
////        List<OrderAdminView> orders = orderDAO.findAllAdminView();
//        req.setAttribute("orders", orders);
//
//        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminOrders.jsp")
//                .forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws IOException {
//
//        req.setCharacterEncoding("UTF-8");
//
//        String action = req.getParameter("action"); // complete/shipping/cancel
//        String idRaw = req.getParameter("id");
//
//        if (action == null || idRaw == null) {
//            resp.sendRedirect(req.getContextPath() + "/AdminOrders");
//            return;
//        }
//
//        int orderId = Integer.parseInt(idRaw);
//
//        String newStatus;
//        switch (action) {
//            case "complete" -> newStatus = "Hoàn tất";
//            case "shipping" -> newStatus = "Đang vận chuyển";
//            case "cancel" -> newStatus = "Đã hủy";
//            default -> {
//                resp.sendRedirect(req.getContextPath() + "/AdminOrders");
//                return;
//            }
//        }
//
//        orderDAO.updateStatus(orderId, newStatus);
//        resp.sendRedirect(req.getContextPath() + "/AdminOrders");
//    }
//}
