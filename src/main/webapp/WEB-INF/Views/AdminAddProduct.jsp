<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <title>Admin — Thêm sản phẩm</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfAdminAddProduct.css">
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
            <a href="${pageContext.request.contextPath}/Admin.jsp">Dashboard</a>
            <a href="${pageContext.request.contextPath}/AdminCategory.jsp">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/AdminProduct.jsp">Quản lí sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminAddProduct.jsp"
               class="active">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminOrders.jsp">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount.jsp">Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/AdminContact.jsp">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>

    <!-- MAIN -->
    <section class="admin-main">

        <!-- TOPBAR -->
        <div class="topbar">
            <div class="top-actions">
                <div class="admin-info">
                    <div class="admin-text">
                        <div class="name">Admin 1</div>
                    </div>
                    <img src="${pageContext.request.contextPath}/Images/Profile/ball.png"
                         class="avatar">
                </div>
            </div>
        </div>

        <!-- TITLE -->
        <div class="page-title">
            <h1>Thêm sản phẩm</h1>
        </div>

        <!-- CONTENT -->
        <div class="content">

            <form class="add-form"
                  action="${pageContext.request.contextPath}/admin/add-product"
                  method="post"
                  enctype="multipart/form-data">

                <!-- Ảnh -->
                <div class="form-group full">
                    <label>Hình ảnh sản phẩm</label>
                    <input type="file" name="images" multiple>
                    <p class="note">* Có thể chọn nhiều ảnh</p>
                </div>

                <div class="form-group">
                    <label>Tên sản phẩm</label>
                    <input type="text" name="productName"
                           placeholder="Nhập tên sản phẩm" required>
                </div>

                <div class="form-group">
                    <label>Giá</label>
                    <input type="number" name="price"
                           placeholder="Nhập giá (VNĐ)" required>
                </div>

                <div class="form-group">
                    <label>Số lượng</label>
                    <input type="number" name="quantity"
                           placeholder="Nhập số lượng" required>
                </div>

                <div class="form-group">
                    <label>Loại</label>
                    <select name="type" id="productType"
                            onchange="showAttributes()" required>
                        <option value="">Chọn danh mục</option>
                        <option value="kinhcan">Kính cận</option>
                        <option value="kinhmat">Kính mát</option>
                        <option value="kinhaptrong">Kính áp tròng</option>
                        <option value="gongkinh">Gọng kính</option>
                    </select>
                </div>

                <div class="form-group full">
                    <label>Mô tả sản phẩm</label>
                    <textarea name="description" rows="5"
                              placeholder="Nhập mô tả chi tiết..."></textarea>
                </div>

                <!-- Thuộc tính động -->
                <div id="attributes" class="add-form"></div>

                <button type="submit" class="btn primary">
                    Lưu sản phẩm
                </button>

            </form>

        </div>

    </section>

</div>

<script src="${pageContext.request.contextPath}/JavaScript/AdminAddProduct.js"></script>
</body>
</html>
