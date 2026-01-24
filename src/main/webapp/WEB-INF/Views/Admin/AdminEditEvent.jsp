<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Sửa Sự Kiện</title>
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
            <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminEvents" class="active">
                Quản lý sự kiện
            </a>
        </nav>
    </aside>

    <!-- CONTENT -->
    <div class="container">
        <form action="${pageContext.request.contextPath}/AdminEditEvent"
              method="post"
              enctype="multipart/form-data">

            <h2>Chỉnh sửa sự kiện</h2>

            <input type="hidden" name="id" value="${event.id}">

            <label>Tên sự kiện</label>
            <input type="text" name="title"
                   value="${event.title}" required>

            <label>Nội dung sự kiện</label>
            <textarea name="content">${event.content}</textarea>

            <label>Ngày bắt đầu</label>
            <input type="date" name="startDate"
                   value="${event.startDate}" required>

            <label>Ngày kết thúc</label>
            <input type="date" name="endDate"
                   value="${event.endDate}" required>

            <label>Loại giảm giá</label>
            <select name="discountType">
                <option value="NONE"
                ${event.discountType == 'NONE' ? 'selected' : ''}>
                    Không giảm
                </option>
                <option value="PERCENT"
                ${event.discountType == 'PERCENT' ? 'selected' : ''}>
                    Giảm theo %
                </option>
                <option value="AMOUNT"
                ${event.discountType == 'AMOUNT' ? 'selected' : ''}>
                    Giảm tiền
                </option>
            </select>

            <label>Giá trị giảm</label>
            <input type="number" step="0.01"
                   name="discountValue"
                   value="${event.discountValue}">

            <label>Sản phẩm áp dụng</label>
            <c:forEach var="p" items="${products}">
                <div>
                    <input type="checkbox"
                           name="productIds"
                           value="${p.id}"
                    <c:if test="${selectedProductIds.contains(p.id)}">
                           checked
                    </c:if>
                    >
                        ${p.product_name}
                </div>
            </c:forEach>

            <label>Banner mới (nếu muốn thay)</label>
            <input type="file" name="banner">

            <button type="submit">Lưu thay đổi</button>
        </form>
    </div>
</div>

</body>
</html>

