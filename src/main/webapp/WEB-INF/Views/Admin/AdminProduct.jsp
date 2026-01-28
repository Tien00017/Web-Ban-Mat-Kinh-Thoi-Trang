<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin - Quản lý sản phẩm</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfAdminProduct.css">
</head>
<body>

<div class="app">

    <!-- SIDEBAR -->
    <aside class="admin-sidebar">
        <h2>Mắt kính Nông Lâm</h2>
        <nav>
            <a class="active" href="${pageContext.request.contextPath}/AdminProduct">
                Quản lý sản phẩm
            </a>
            <a href="${pageContext.request.contextPath}/AdminAddProduct">
                Thêm sản phẩm
            </a>
        </nav>
    </aside>

    <!-- MAIN -->
    <section class="main">
        <h1>Danh sách sản phẩm</h1>

        <table>
            <thead>
            <tr>
                <th>Hình</th>
                <th>Tên</th>
                <th>Loại</th>
                <th>Giá</th>
                <th>Kho</th>
                <th>Màu</th>
                <th>Chất liệu</th>
                <th>Kiểu</th>
                <th></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="p" items="${products}">
                <tr>
                    <td>
                        <img src="${pageContext.request.contextPath}/Images/${p.imageUrl}"
                             width="60">
                    </td>
                    <td>${p.productName}</td>
                    <td>${p.categoryName}</td>
                    <td>${p.price}</td>
                    <td>${p.stock}</td>
                    <td>${p.color}</td>
                    <td>${p.material}</td>
                    <td>${p.style}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/AdminAddProduct?id=${p.id}">
                            Sửa
                        </a>
                        |
                        <a href="${pageContext.request.contextPath}/AdminProduct?action=delete&id=${p.id}"
                           onclick="return confirm('Xóa sản phẩm này?')">
                            Xóa
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>

</div>

</body>
</html>
