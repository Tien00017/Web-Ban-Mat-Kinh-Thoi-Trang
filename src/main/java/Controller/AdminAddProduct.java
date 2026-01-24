package Controller;

import ...

@WebServlet("/admin/product/add")
@MultipartConfig
public class AdminAddProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Product p = new Product();
        p.setName(req.getParameter("productName"));
        p.setPrice(Double.parseDouble(req.getParameter("price")));
        p.setQuantity(Integer.parseInt(req.getParameter("quantity")));
        p.setBrand("NongLam"); // tạm
        p.setCategoryId(1); // mapping sau
        p.setDescription(req.getParameter("description"));

        Part file = req.getPart("images");
        String fileName = System.currentTimeMillis() + "_" + file.getSubmittedFileName();
        String uploadPath = getServletContext().getRealPath("/Images");

        file.write(uploadPath + File.separator + fileName);
        p.setImage(fileName);

        new ProductDAO().insert(p);

        resp.sendRedirect(req.getContextPath() + "/admin/product");
    }
}
