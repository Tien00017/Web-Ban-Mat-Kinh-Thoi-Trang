<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <title>Admin - Đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminOrders.css">
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
            <a href="${pageContext.request.contextPath}/AdminOrders" class="active">Quản lí đơn hàng</a>
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
            <h1>Quản lý đơn hàng</h1>
        </div>

        <div class="content">
            <div class="left">
                <table>
                    <thead>
                    <tr>
                        <th>MÃ ĐƠN</th>
                        <th>KHÁCH HÀNG</th>
                        <th>SỐ ĐIỆN THOẠI</th>
                        <th>SẢN PHẨM</th>
                        <th>SỐ LƯỢNG</th>
                        <th>NGÀY ĐẶT</th>
                        <th>TRẠNG THÁI</th>
                        <th>TỔNG TIỀN</th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <tr>
                        <td>OD1023</td>
                        <td>Nguyễn Văn A</td>
                        <td>0978276791</td>
                        <td>Kính Cận C07</td>
                        <td>1</td>
                        <td>14/02/2025</td>
                        <td><span class="stock-low">Chờ xử lý</span></td>
                        <td>1,250,000 VNĐ</td>
                        <td class="text-align">
                            <button class="btn ghost">Hoàn thành</button>
                            <button class="btn ghost">Đang vận chuyển</button>
                            <button class="btn ghost">Hủy</button>
                        </td>
                    </tr>
                    <tr>
                        <td>OD1047</td>
                        <td>Trần Thị B</td>
                        <td>0813939729</td>
                        <td>Kính Mát R27</td>
                        <td>1</td>
                        <td>14/02/2025</td>
                        <td><span class="stock-high">Hoàn tất</span></td>
                        <td>990,000 VNĐ</td>
                        <td class="text-align">
                            <button class="btn ghost">Hoàn thành</button>
                            <button class="btn ghost">Đang vận chuyển</button>
                            <button class="btn ghost">Hủy</button>
                        </td>
                    </tr>

                    <tr>
                        <td>OD1023</td>
                        <td>Nguyễn Văn A</td>
                        <td>0978276791</td>
                        <td>Kính Cận C07</td>
                        <td>1</td>
                        <td>14/02/2025</td>
                        <td><span class="stock-high">Đang vận chuyển</span></td>
                        <td>1,250,000 VNĐ</td>
                        <td class="text-align">
                            <button class="btn ghost">Hoàn thành</button>
                            <button class="btn ghost">Đang vận chuyển</button>
                            <button class="btn ghost">Hủy</button>
                        </td>
                    </tr>
                    <tr>
                        <td>OD1033</td>
                        <td>Phạm C</td>
                        <td>0385667219</td>
                        <td>Kính Áp Tròng</td>
                        <td>1</td>
                        <td>14/02/2025</td>
                        <td><span class="stock-out ">Đã hủy</span></td>
                        <td>320,000 VNĐ</td>
                        <td class="text-align">
                            <button class="btn ghost">Hoàn thành</button>
                            <button class="btn ghost">Đang vận chuyển</button>
                            <button class="btn ghost">Hủy</button>
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
