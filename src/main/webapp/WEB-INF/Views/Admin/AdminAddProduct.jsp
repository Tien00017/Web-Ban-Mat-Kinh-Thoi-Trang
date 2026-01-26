<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
            <a href="${pageContext.request.contextPath}/AdminAccount" >Quản lí sự kiện</a>
            <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>

    <div class="container">
        <h1 class="page-title">Thêm sản phẩm mới</h1>

        <form class="add-form">

            <!-- Ảnh sản phẩm -->
            <div class="form-group full">
                <label>Hình ảnh sản phẩm</label>
                <input type="file" multiple>
                <p class="note">* Có thể chọn nhiều ảnh</p>
            </div>

            <!-- Tên + giá -->
            <div class="form-group">
                <label>Tên sản phẩm</label>
                <input type="text" placeholder="Nhập tên sản phẩm">
            </div>

            <div class="form-group">
                <label>Giá</label>
                <input type="number" placeholder="Nhập giá (VNĐ)">
            </div>

            <div class="form-group">
                <label>Số lượng</label>
                <input type="number" placeholder="Nhập số lượng">
            </div>


            <div class="form-group">
                <label>Loại</label>
                <select class="productType" id="productType" onchange="showAttributes()">
                    <option value="">Chọn danh mục cho sản phẩm</option>
                    <option value="kinhcan">Kính cận</option>
                    <option value="kinhmat">Kính mát</option>
                    <option value="kinhaptrong">Kính áp tròng</option>
                    <option value="gongkinh">Gọng kính</option>
                </select>
            </div>

            <div class="form-group full">
                <label>Mô tả sản phẩm</label>
                <textarea rows="5" placeholder="Nhập mô tả chi tiết..."></textarea>
            </div>

            <!--thay đổi theo loại sản phẩm-->
            <div id="attributes" class="add-form"></div>


            <button class="submit-btn" type="submit">Lưu sản phẩm</button>

        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/JavaScript/AdminAddProduct.js"></script>
</body>
</html>
