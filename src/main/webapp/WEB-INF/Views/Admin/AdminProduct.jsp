<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
    <html lang="vi">
    <head>
        <meta charset="utf-8">
        <title>Admin - Quản lý sản phẩm</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminProduct.css">
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
                <a href="${pageContext.request.contextPath}/AdminProduct" class="active">Quản lí sản phẩm</a>
                <a href="${pageContext.request.contextPath}/AdminAddProduct">Thêm sản phẩm</a>
                <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
                <a href="${pageContext.request.contextPath}/AdminAccount" >Quản lí tài khoản</a>
                <a href="${pageContext.request.contextPath}/AdminAccount" >Thêm sự kiện</a>
                <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
            </nav>

            <div class="side-footer muted">
                Admin • Bạn đang đăng nhập với quyền Quản trị
            </div>
        </aside>
        <section class="main">
            <div class="page-title">
                <h1>Quản lý sản phẩm</h1>
            </div>

            <div class="content">
                <!-- LEFT -->
                <div class="left">
                    <table>
                        <thead>
                        <tr>
                            <th>SẢN PHẨM</th>
                            <th>DANH MỤC</th>
                            <th>GIÁ</th>
                            <th>KHO</th>
                            <th>THƯƠNG HIỆU</th>
                            <th>TRẠNG THÁI</th>
                            <th></th>
                        </tr>
                        </thead>

                        <tbody>
                        <tr>
                            <td>
                                <div class="prod-info">
                                    <img src="${pageContext.request.contextPath}/Images/KinhCan/KinhCan1.jpg" class="thumb" alt="Kính Cận C07">
                                    <div>
                                        <div class="fw">Kính Cận C07</div>
                                        <div class="muted">Mã: C07</div>
                                    </div>
                                </div>
                            </td>
                            <td>Kính Cận</td>
                            <td>1,250,000</td>
                            <td>120</td>
                            <td>Mắt Kính Điện Biên Phủ</td>
                            <td><span class="stock-high">Nhiều</span></td>
                            <td class="text-right">
                                <button class="btn ghost">Sửa</button>

                            </td>
                        </tr>

                        <tr>
                            <td>
                                <div class="prod-info">
                                    <img src="${pageContext.request.contextPath}/Images/KinhMat/KinhMat2.jpg" class="thumb" alt="Kính Mát R27">
                                    <div>
                                        <div class="fw">Kính Mát R27</div>
                                        <div class="muted">Mã: R27</div>
                                    </div>
                                </div>
                            </td>
                            <td>Kính Mát</td>
                            <td>990,000</td>
                            <td>0</td>
                            <td>Mắt Kính Sài Gòn</td>
                            <td><span class="stock-out">Hết</span></td>
                            <td class="text-right">
                                <button class="btn ghost">Sửa</button>

                            </td>
                        </tr>

                        <tr>
                            <td>
                                <div class="prod-info">
                                    <img src="${pageContext.request.contextPath}/Images/KinhApTrong/KinhApTrong3.jpg" class="thumb" alt="Kính Áp Tròng AT02">
                                    <div>
                                        <div class="fw">Kính Áp Tròng AT02</div>
                                        <div class="muted">Mã: AT02</div>
                                    </div>
                                </div>
                            </td>
                            <td>Kính Áp Tròng</td>
                            <td>2,990,000</td>
                            <td>10</td>
                            <td>Mắt Kính Sài Gòn</td>
                            <td><span class="stock-low">Ít</span></td>
                            <td class="text-right">
                                <button class="btn ghost">Sửa</button>

                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <!-- RIGHT -->
<!--                <aside class="right">-->
<!--                    <h3>Thêm sản phẩm nhanh</h3>-->

<!--                    <div class="form-row">-->
<!--                        <input type="text" placeholder="Tên sản phẩm">-->
<!--                        <input type="text" placeholder="Mã sản phẩm" class="small">-->
<!--                    </div>-->

<!--                    <div class="form-row">-->
<!--                        <input type="text" placeholder="Thương hiệu" class="small">-->
<!--                        <select class="small">-->
<!--                            <option>LOẠI</option>-->
<!--                            <option>KÍNH CẬN</option>-->
<!--                            <option>KÍNH MÁT</option>-->
<!--                            <option>KÍNH ÁP TRÒNG</option>-->
<!--                            <option>GỌNG KÍNH</option>-->
<!--                        </select>-->
<!--                    </div>-->

<!--                    <div class="form-row">-->
<!--                        <input type="number" placeholder="Giá (VND)">-->
<!--                        <input type="number" placeholder="Kho" class="small">-->
<!--                    </div>-->

<!--                    <textarea rows="4" placeholder="Mô tả ngắn"></textarea>-->

<!--                    <div class="form-actions">-->
<!--                        <button class="btn">Lưu sản phẩm</button>-->
<!--                    </div>-->
<!--                </aside>-->
            </div>
        </section>


    </div>
    </body>
    </html>
