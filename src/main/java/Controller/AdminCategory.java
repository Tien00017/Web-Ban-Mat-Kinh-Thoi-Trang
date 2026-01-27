package Controller;

import Model.Object.Category;
import Model.Service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/AdminCategory")
public class AdminCategory extends HttpServlet {

    private final CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> categories = categoryService.getAllCategories();
        req.setAttribute("categories", categories);

        req.getRequestDispatcher("/WEB-INF/Views/Admin/AdminCategory.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create" -> {
                    categoryService.createCategory(req.getParameter("name"));
                    resp.sendRedirect(req.getContextPath() + "/AdminCategory?ok=created");
                }
                case "update" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    categoryService.updateCategory(id, req.getParameter("name"));
                    resp.sendRedirect(req.getContextPath() + "/AdminCategory?ok=updated");
                }
                case "delete" -> {
                    int id = Integer.parseInt(req.getParameter("id"));
                    categoryService.deleteCategory(id);
                    resp.sendRedirect(req.getContextPath() + "/AdminCategory?ok=deleted");
                }
                default -> resp.sendRedirect(req.getContextPath() + "/AdminCategory");
            }
        } catch (IllegalArgumentException e) {
            resp.sendRedirect(req.getContextPath() + "/AdminCategory?err=" + e.getMessage());
        } catch (IllegalStateException e) {
            resp.sendRedirect(req.getContextPath() + "/AdminCategory?err=" + e.getMessage());
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/AdminCategory?err=fail");
        }
    }
}
