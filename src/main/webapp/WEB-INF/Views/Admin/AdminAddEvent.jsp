<%--
  Created by IntelliJ IDEA.
  User: phamn
  Date: 1/22/2026
  Time: 5:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Thêm Sự Kiện</title>
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
            <a href="${pageContext.request.contextPath}/Admin" >Dashboard</a>
            <a href="${pageContext.request.contextPath}/AdminCategory">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/AdminProduct">Quản lí sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminAddProduct">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount" >Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/AdminAccount" class="active">Thêm sự kiện</a>
            <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>
    <div class="container">
        <form action="${pageContext.request.contextPath}/AdminAddEvent"
              method="post"
              enctype="multipart/form-data">

            <h2>Thêm sự kiện mới</h2>

            <label>Tên sự kiện</label>
            <input type="text" name="title" required>

            <label>Nội dung sự kiện</label>
            <textarea name="content"></textarea>

            <label>Ngày bắt đầu</label>
            <input type="date" name="startDate" required>

            <label>Ngày kết thúc</label>
            <input type="date" name="endDate" required>

            <label>Loại giảm giá</label>
            <select name="discountType">
                <option value="NONE">Không giảm</option>
                <option value="PERCENT">Giảm theo %</option>
                <option value="AMOUNT">Giảm tiền</option>
            </select>

            <label>Giá trị giảm</label>
            <input type="number" name="discountValue" step="0.01">

            <label>Sản phẩm áp dụng</label>
            <c:forEach var="p" items="${products}">
                <div>
                    <input type="checkbox" name="productIds" value="${p.id}">
                        ${p.product_name}
                </div>
            </c:forEach>

            <label>Banner sự kiện</label>
            <input type="file" name="banner">

            <button type="submit">Tạo sự kiện</button>
        </form>

    </div>
</div>

</body>
</html>
