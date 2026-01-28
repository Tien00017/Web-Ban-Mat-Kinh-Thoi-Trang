<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminAddProduct.css">
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
            <a href="${pageContext.request.contextPath}/AdminAddProduct"class="active">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount" >Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/admin/event/list" >Quản lí sự kiện</a>
            <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>

    <div class="container">
        <h1 class="page-title">Thêm sản phẩm mới</h1>

        <form class="add-form"
              action="${pageContext.request.contextPath}/AdminAddProduct"
              method="post"
              enctype="multipart/form-data">

            <!-- Ảnh URL (tùy chọn) -->
            <div class="form-group full">
                <label>Ảnh chính (URL) - tùy chọn</label>
                <input type="url" name="mainImageUrl" placeholder="https://...">
                <p class="note">Nếu bạn upload ảnh file thì sẽ ưu tiên ảnh file.</p>
            </div>

            <!-- Upload ảnh file (tùy chọn, có thể nhiều ảnh) -->
            <div class="form-group full">
                <label>Upload ảnh sản phẩm (có thể chọn nhiều)</label>
                <input type="file" name="images" multiple accept="image/*">
                <p class="note">Ảnh đầu tiên sẽ được đặt làm ảnh chính.</p>
            </div>

            <div class="form-group">
                <label>Tên sản phẩm</label>
                <input type="text" name="productName" required>
            </div>

            <div class="form-group">
                <label>Giá</label>
                <input type="number" name="price" min="0" required>
            </div>

            <div class="form-group">
                <label>Số lượng</label>
                <input type="number" name="stock" min="0" required>
            </div>

            <div class="form-group">
                <label>Thương hiệu</label>
                <input type="text" name="brand">
            </div>

            <div class="form-group">
                <label>Xuất xứ</label>
                <input type="text" name="origin">
            </div>

            <div class="form-group">
                <label>Danh mục</label>
                <select name="categoryId" required>
                    <option value="">Chọn danh mục</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}">${c.categoryName}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group full">
                <label>Mô tả chi tiết</label>
                <textarea name="generalDescription" rows="4"></textarea>
            </div>

            <div class="form-group full">
                <label>Thông tin vận chuyển</label>
                <textarea name="shippingInfo" rows="2"></textarea>
            </div>

            <div class="form-group full">
                <label>Bảo hành</label>
                <textarea name="guaranteeDetails" rows="2"></textarea>
            </div>

            <button class="submit-btn" type="submit">Lưu sản phẩm</button>
        </form>


    </div>
</div>
<script src="${pageContext.request.contextPath}/JavaScript/AdminAddProduct.js"></script>
</body>
</html>
