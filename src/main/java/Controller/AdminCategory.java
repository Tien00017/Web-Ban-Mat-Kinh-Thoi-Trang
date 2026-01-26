package Controller;

import Model.DAO.CategoryDAO;
import Model.Object.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
@WebServlet("/AdminCategory")
public class AdminCategory extends HttpServlet {

    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> categories = categoryDAO.findAll();
        System.out.println("Categories size = " + categories.size());
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminCategory.jsp")
                .forward(req, resp);
        System.out.println("== AdminCategory doGet RUN ==");
        System.out.println("ContextPath = " + req.getContextPath());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "create" -> create(req, resp);
            case "update" -> update(req, resp);
            case "delete" -> delete(req, resp);
            default -> resp.sendRedirect(req.getContextPath() + "/AdminCategory");
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        if (name == null) name = "";
        name = name.trim();

        if (name.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/AdminCategory?err=empty");
            return;
        }

        if (categoryDAO.existsByName(name)) {
            resp.sendRedirect(req.getContextPath() + "/AdminCategory?err=exists");
            return;
        }

        categoryDAO.insert(name);
        resp.sendRedirect(req.getContextPath() + "/AdminCategory?ok=created");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        if (name == null) name = "";
        name = name.trim();

        if (name.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/AdminCategory?err=empty");
            return;
        }

        categoryDAO.update(id, name);
        resp.sendRedirect(req.getContextPath() + "/AdminCategory?ok=updated");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        if (categoryDAO.hasProducts(id)) {
            resp.sendRedirect(req.getContextPath() + "/AdminCategory?err=has_products");
            return;
        }

        categoryDAO.delete(id);
        resp.sendRedirect(req.getContextPath() + "/AdminCategory?ok=deleted");
    }
}