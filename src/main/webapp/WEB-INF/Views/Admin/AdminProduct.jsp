<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
            <a href="${pageContext.request.contextPath}/Admin">Dashboard</a>
            <a href="${pageContext.request.contextPath}/AdminCategory">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/AdminProduct" class="active">Quản lí sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminAddProduct">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount">Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/admin/event/list">Quản lí sự kiện</a>
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
                    <c:forEach var="p" items="${products}">
                        <tr>
                            <td>
                                <div class="prod-info">
                                    <!-- Ảnh chính: nếu có mainImageUrl thì show, không thì bỏ trống -->
                                    <c:if test="${not empty p.mainImageUrl}">
                                        <img src="${p.mainImageUrl.startsWith('http') ? p.mainImageUrl : pageContext.request.contextPath.concat('/').concat(p.mainImageUrl)}"
                                             class="thumb" alt="${p.productName}">
                                    </c:if>

                                    <div>
                                        <div class="fw">${p.productName}</div>
                                        <div class="muted">ID: ${p.id}</div>
                                    </div>
                                </div>
                            </td>

                            <td>${p.categoryName}</td>
                            <td>${p.price}</td>
                            <td>${p.stock}</td>
                            <td>${p.brand}</td>

                            <td>
            <span class="${p.stock == 0 ? 'stock-out' : (p.stock < 10 ? 'stock-low' : 'stock-high')}">
                    ${p.stock == 0 ? 'Hết' : (p.stock < 10 ? 'Ít' : 'Nhiều')}
            </span>
                            </td>

                            <td class="text-right">
                                <a class="btn ghost"
                                   href="${pageContext.request.contextPath}/AdminAddProduct?id=${p.id}">Sửa</a>

                                <!-- Xóa (có thể dùng GET như bạn đang làm, nhưng POST chuẩn hơn) -->
                                <form action="${pageContext.request.contextPath}/AdminProduct" method="post"
                                      style="display:inline;"
                                      onsubmit="return confirm('Xóa sản phẩm này?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${p.id}">
                                    <button type="submit" class="btn ghost">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty products}">
                        <tr>
                            <td colspan="7" class="muted">Chưa có sản phẩm.</td>
                        </tr>
                    </c:if>
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
