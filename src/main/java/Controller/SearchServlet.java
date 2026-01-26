@WebServlet("/Search")
public class SearchServlet extends HttpServlet {

    final ProductService productService = new ProductService();
    final NewsService newsService = new NewsService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String keyword = request.getParameter("keyword");

        // Nếu người dùng bấm tìm nhưng không nhập gì
        if (keyword == null || keyword.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Home");
            return;
        }

        request.setAttribute("keyword", keyword);
        request.setAttribute("products", productService.search(keyword));
        request.setAttribute("newsList", newsService.search(keyword));

        request.getRequestDispatcher("/Views/SearchResult.jsp")
                .forward(request, response);
    }
}
