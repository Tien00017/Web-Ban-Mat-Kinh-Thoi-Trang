<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Quản lý sự kiện</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminAddEvent.css.css">
</head>
<body>

<div class="app">
    <!-- SIDEBAR -->
    <aside class="admin-sidebar">
        <div class="brand">
            <img src="${pageContext.request.contextPath}/Images/Logo.jpg" class="logo-img" alt="Logo">
            <h2>Mắt kính Nông Lâm</h2>
        </div>

        <nav class="admin-nav">
            <a href="${pageContext.request.contextPath}/Admin">Dashboard</a>
            <a href="${pageContext.request.contextPath}/AdminCategory">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/AdminProduct">Quản lí sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminAddProduct">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount">Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/AdminEvents" class="active">Quản lý sự kiện</a>
            <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>

    <!-- CONTENT -->
    <div class="container">
        <h2>Danh sách sự kiện</h2>

        <a href="${pageContext.request.contextPath}/AdminAddEvent"
           style="margin-bottom: 15px; display: inline-block;">
            ➕ Thêm sự kiện mới
        </a>

        <table border="1" width="100%" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>ID</th>
                <th>Tên sự kiện</th>
                <th>Thời gian</th>
                <th>Giảm giá</th>
                <th>Trạng thái</th>
                <th>Thao tác</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="e" items="${events}">
                <tr>
                    <td>${e.id}</td>
                    <td>${e.title}</td>
                    <td>
                            ${e.startDate} → ${e.endDate}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${e.discountType == 'PERCENT'}">
                                ${e.discountValue} %
                            </c:when>
                            <c:when test="${e.discountType == 'AMOUNT'}">
                                ${e.discountValue} đ
                            </c:when>
                            <c:otherwise>
                                Không giảm
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${e.status}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/AdminEditEvent?id=${e.id}">
                            ✏️ Sửa
                        </a>
                    </td>
                </tr>
            </c:forEach>

            <c:if test="${empty events}">
                <tr>
                    <td colspan="6" align="center">
                        Chưa có sự kiện nào
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>

