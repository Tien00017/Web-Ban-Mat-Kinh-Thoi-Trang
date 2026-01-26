<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <a href="${pageContext.request.contextPath}/AdminOrders" >Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount">Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/admin/event/list" class="active">Quản lí sự kiện</a>
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
                    <c:forEach var="o" items="${orders}">
                        <tr>
                            <td>OD${o.id}</td>
                            <td>${o.customerName}</td>
                            <td>${o.phone}</td>
                            <td>${o.productsSummary}</td>
                            <td>${o.totalQuantity}</td>
                            <td>${o.createdAt}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${o.status == 'Chờ xử lý'}">
                                        <span class="stock-low">${o.status}</span>
                                    </c:when>
                                    <c:when test="${o.status == 'Đã hủy'}">
                                        <span class="stock-out">${o.status}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="stock-high">${o.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>${o.totalAmount} VNĐ</td>

                            <td class="text-align">
                                <form action="${pageContext.request.contextPath}/AdminOrders" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="${o.id}">
                                    <input type="hidden" name="action" value="complete">
                                    <button class="btn ghost" type="submit">Hoàn thành</button>
                                </form>

                                <form action="${pageContext.request.contextPath}/AdminOrders" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="${o.id}">
                                    <input type="hidden" name="action" value="shipping">
                                    <button class="btn ghost" type="submit">Đang vận chuyển</button>
                                </form>

                                <form action="${pageContext.request.contextPath}/AdminOrders" method="post" style="display:inline;"
                                      onsubmit="return confirm('Bạn chắc chắn muốn hủy đơn?');">
                                    <input type="hidden" name="id" value="${o.id}">
                                    <input type="hidden" name="action" value="cancel">
                                    <button class="btn ghost" type="submit">Hủy</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </div>
        </div>

    </section>

</div>
</body>
</html>
