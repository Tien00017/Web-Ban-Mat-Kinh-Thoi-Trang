<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin-Quản lí tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminAccount.css">
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
            <a href="${pageContext.request.contextPath}/AdminAccount" class="active">Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>


    <!-- MAIN -->
    <section class="main">

        <div class="page-title">
            <h1>Quản lý tài khoản</h1>
        </div>

        <div class="panel">
            <div class="left">
                <table class="accounts-table">
                    <thead>
                    <tr>
                        <th style="width:60px">ID</th>
                        <th style="width:220px">TÊN</th>
                        <th style="width:170px">SỐ ĐIỆN THOẠI</th>
                        <th style="width:220px">EMAIL</th>
                        <th style="width:120px">NGÀY SINH</th>
                        <th style="width:120px">GIỚI TÍNH</th>
                        <th>ĐỊA CHỈ</th>
                        <th>TRẠNG THÁI</th>
                        <th style="width:150px">VAI TRÒ</th>
                        <th style="width:150px"></th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <td>U001</td>
                        <td>Nguyễn Văn A</td>
                        <td>0978276791</td>
                        <td>nguyenvana@example.com</td>
                        <td>1990-02-15</td>
                        <td>Nam</td>
                        <td>123 ĐBP, Q.1, TP.HCM</td>
                        <td><span class="open">Mở</span></td>
                        <td><span class="tag success">user</span></td>
                        <td class="text-right">
                            <button class="btn ghost ">Sửa</button>
                        </td>
                    </tr>

                    <tr>
                        <td>U002</td>
                        <td>Trần Thị B</td>
                        <td>0813939729</td>
                        <td>tranthib@example.com</td>
                        <td>1994-07-10</td>
                        <td>Nữ</td>
                        <td>45 Lý Tự Trọng, Q.3</td>
                        <td><span class="open">Mở</span></td>
                        <td><span class="tag pending">admin</span></td>
                        <td class="text-right">
                            <button class="btn ghost ">Sửa</button>
                        </td>
                    </tr>

                    <tr>
                        <td>U003</td>
                        <td>Phạm C</td>
                        <td>0385667219</td>
                        <td>phamc@example.com</td>
                        <td>1986-11-22</td>
                        <td>Khác</td>
                        <td>78 Nguyễn Huệ, Q.1</td>
                        <td><span class="close">Khóa</span></td>
                        <td><span class="tag success">user</span></td>
                        <td class="text-right">
                            <button class="btn ghost ">Sửa</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </section>


</div>
</body>
</html>