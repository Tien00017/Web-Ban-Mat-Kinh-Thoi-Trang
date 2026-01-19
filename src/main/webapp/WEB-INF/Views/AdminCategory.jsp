<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <title>Admin - Quản lý danh mục</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminCategory.css">
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
            <a href="${pageContext.request.contextPath}/AdminCategory" class="active">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/AdminProduct">Quản lí sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminAddProduct">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount">Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>

    <!-- MAIN -->
    <section class="main">

        <div class="page-title">
            <h1>Quản lý danh mục</h1>
        </div>

        <div class="content">

            <div class="left">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>TÊN DANH MỤC</th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Kính Cận</td>
                        <td class="actions">
                            <button class="btn ghost">Sửa</button>
                            <button class="btn ghost">Xóa</button>
                        </td>
                    </tr>

                    <tr>
                        <td>2</td>
                        <td>Kính Mát</td>
                        <td class="actions">
                            <button class="btn ghost">Sửa</button>
                            <button class="btn ghost">Xóa</button>
                        </td>
                    </tr>

                    <tr>
                        <td>3</td>
                        <td>Kính Áp Tròng</td>
                        <td class="actions">
                            <button class="btn ghost">Sửa</button>
                            <button class="btn ghost">Xóa</button>
                        </td>
                    </tr>
                    </tbody>

                </table>
            </div>

            <aside class="right">
                <h3>Thêm danh mục</h3>

                <div class="form-row">
                    <input type="text" placeholder="Tên Danh Mục">
                </div>

                <div class="form-actions">
                    <button class="btn">Lưu</button>
                </div>
            </aside>

        </div>

    </section>


</div>
</body>
</html>
