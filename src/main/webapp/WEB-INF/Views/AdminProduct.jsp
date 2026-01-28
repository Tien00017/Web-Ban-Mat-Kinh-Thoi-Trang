<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin - Quản lý sản phẩm</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfAdminProduct.css">
</head>

<body>
<div class="app">

    <section class="main">
        <div class="page-title">
            <h1>Quản lý sản phẩm</h1>
        </div>

        <div class="content">
            <table>
                <thead>
                <tr>
                    <th>TÊN SẢN PHẨM</th>
                    <th>GIÁ</th>
                    <th>KHO</th>
                    <th>THƯƠNG HIỆU</th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td>${p.productName}</td>
                        <td>${p.price}</td>
                        <td>${p.stock}</td>
                        <td>${p.brand}</td>
                        <td class="text-right">
                            <a class="btn ghost"
                               href="${pageContext.request.contextPath}/AdminAddProduct?id=${p.id}">
                                Sửa
                            </a>
                            <a class="btn ghost"
                               href="${pageContext.request.contextPath}/AdminProduct?action=delete&id=${p.id}"
                               onclick="return confirm('Xóa sản phẩm này?')">
                                Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

</div>
</body>
</html>
