<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Quản lý sự kiện</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminEvent.css">
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
            <a href="${pageContext.request.contextPath}/admin/event/list" class="active">Quản lí sự kiện</a>
            <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>

    <!-- CONTENT -->
    <div class="container">
        <h2>Quản lý sự kiện / khuyến mãi</h2>

        <a href="${pageContext.request.contextPath}/admin/event/add" class="btn btn-primary">
            + Thêm sự kiện
        </a>

        <p>số lượng sự kiện:
            <c:out value="${events != null ? events.size() : 'null'}"/>
        </p>
        <table border="1" width="100%" cellpadding="8">
            <tr>
                <th>ID</th>
                <th>Tiêu đề</th>
                <th>Thời gian</th>
                <th>Giảm giá</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>

            <c:forEach items="${events}" var="e">
                <tr>
                    <td>${e.id}</td>
                    <td>${e.title}</td>
                    <td>
                            ${e.startDate} → ${e.endDate}
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${e.discountType == 'PERCENT'}">
                                ${e.discountValue}%
                            </c:when>
                            <c:otherwise>
                                ${e.discountValue} VNĐ
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <c:set var="statusLabel"
                           value="${e.status == 'ACTIVE' ? 'Hoạt động' :
                                    e.status == 'INACTIVE' ? 'Tạm dừng' :
                                    e.status == 'EXPIRED' ? 'Hết hạn' : 'Không rõ'}" />

                    <td>
                        <span class="status ${e.status}">
                            ${statusLabel}
                        </span>
                    </td>

                    <td>
                        <a href="${pageContext.request.contextPath}/admin/event/edit?id=${e.id}">Sửa</a> |
                        <a href="${pageContext.request.contextPath}/admin/event/delete?id=${e.id}"
                           onclick="return confirm('Xóa sự kiện này?')">Xóa</a>

                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

</body>
</html>

