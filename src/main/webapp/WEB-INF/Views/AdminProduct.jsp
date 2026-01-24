<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <title>Admin - Quản lý sản phẩm</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfAdminProduct.css">
</head>

<body>
<div class="app">

    <!-- SIDEBAR -->
    <aside class="admin-sidebar">
        <div class="brand">
            <img src="${pageContext.request.contextPath}/Images/Logo.jpg"
                 class="logo-img" alt="Logo">
            <h2>Mắt kính Nông Lâm</h2>
        </div>

        <nav class="admin-nav">
            <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/admin/category">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/admin/product" class="active">Quản lí sản phẩm</a>
            <a href="${pageContext.request.contextPath}/admin/product/add">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/admin/orders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/admin/account">Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/admin/contact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>

    <!-- MAIN -->
    <section class="main">
        <div class="page-title">
            <h1>Quản lý sản phẩm</h1>
        </div>

        <div class="content">
            <div class="left">
                <table>
                    <thead>
                    <tr>
                        <th>SẢN PHẨM</th>
                        <th>DANH MỤC</th>
                        <th>GIÁ</th>
                        <th>KHO</th>
                        <th>THƯƠNG HIỆU</th>
                        <th>TRẠNG THÁI</th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- DỮ LIỆU TỪ SERVLET -->
                    <c:forEach var="p" items="${products}">
                        <tr>
                            <td>
                                <div class="prod-info">
                                    <img src="${pageContext.request.contextPath}/Images/${p.image}"
                                         class="thumb" alt="${p.name}">
                                    <div>
                                        <div class="fw">${p.name}</div>
                                        <div class="muted">Mã: ${p.code}</div>
                                    </div>
                                </div>
                            </td>
                            <td>${p.categoryName}</td>
                            <td>${p.price}</td>
                            <td>${p.quantity}</td>
                            <td>${p.brand}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${p.quantity == 0}">
                                        <span class="stock-out">Hết</span>
                                    </c:when>
                                    <c:when test="${p.quantity < 20}">
                                        <span class="stock-low">Ít</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="stock-high">Nhiều</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-right">
                                <a class="btn ghost"
                                   href="${pageContext.request.contextPath}/admin/product?action=edit&id=${p.id}">
                                    Sửa
                                </a>
                                <a class="btn ghost"
                                   href="${pageContext.request.contextPath}/admin/product?action=delete&id=${p.id}"
                                   onclick="return confirm('Xóa sản phẩm này?')">
                                    Xóa
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </div>
        </div>
    </section>

</div>
</body>
</html>
