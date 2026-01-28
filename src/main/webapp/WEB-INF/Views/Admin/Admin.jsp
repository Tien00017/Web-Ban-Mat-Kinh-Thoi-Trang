<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
=

<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1"/>
    <title>Admin Dashboard — Mắt kính Nông Lâm</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfAdmin.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
            <a href="${pageContext.request.contextPath}/Admin" class="active">Dashboard</a>
            <a href="${pageContext.request.contextPath}/AdminCategory">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/AdminProduct">Quản lí sản phẩm</a>
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

    <!-- MAIN -->
    <section class="admin-main">

        <div class="topbar">
            <div class="top-actions">
                <div class="admin-info">
                    <div class="admin-text">
                        <div class="name">Admin</div>
                    </div>
                    <img src="${pageContext.request.contextPath}/Images/Profile/ball.png" class="avatar">
                </div>
            </div>
        </div>

        <!-- STATS -->
        <div class="grid">
            <div class="card">
                <div class="stat-big">${stats.revenue} VNĐ</div>
                <div class="stat-muted">Doanh thu (Hoàn tất)</div>
            </div>
            <div class="card">
                <div class="stat-big">${stats.totalOrders}</div>
                <div class="stat-muted">Tổng số đơn</div>
            </div>
            <div class="card">
                <div class="stat-big">${stats.newCustomers}</div>
                <div class="stat-muted">Khách hàng mới (30 ngày)</div>
            </div>
        </div>

        <!-- CHARTS -->
        <div class="charts-grid">

            <!-- Revenue line -->
            <div class="card chart-card">
                <h3 style="margin-bottom:10px;">Doanh thu 7 ngày gần nhất</h3>
                <canvas id="revChart"></canvas>
            </div>

            <!-- Order status pie/doughnut -->
            <div class="card chart-card">
                <h3 style="margin-bottom:10px;">Tỉ lệ trạng thái đơn</h3>
                <canvas id="statusChart"></canvas>
            </div>

        </div>


        <!-- RECENT ORDERS -->
        <div class="content" style="margin-top:16px;">
            <div class="left">
                <h3>Đơn hàng mới</h3>

                <table>
                    <thead>
                    <tr>
                        <th>MÃ</th>
                        <th>KHÁCH HÀNG</th>
                        <th>SỐ ĐIỆN THOẠI</th>
                        <th>SẢN PHẨM</th>
                        <th>SỐ LƯỢNG</th>
                        <th>TRẠNG THÁI</th>
                        <th>TỔNG</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="o" items="${stats.recentOrders}">
                        <tr>
                            <td>OD${o.id}</td>
                            <td>${o.name}</td>
                            <td>${o.phone}</td>
                            <td>${o.productsSummary}</td>
                            <td>${o.totalQty}</td>
                            <td>
                                <span class="${o.status == 'Chờ xử lý' ? 'stock-low' : (o.status == 'Đã hủy' ? 'stock-out' : 'stock-high')}">
                                        ${o.status}
                                </span>
                            </td>
                            <td>${o.totalAmount} VNĐ</td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty stats.recentOrders}">
                        <tr>
                            <td colspan="7" class="muted">Chưa có đơn hàng.</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- RECENT PRODUCTS -->
        <div class="card" style="margin-top:16px;">
            <h3>Danh sách sản phẩm mới</h3>

            <table>
                <thead>
                <tr>
                    <th>SẢN PHẨM</th>
                    <th>LOẠI</th>
                    <th>GIÁ</th>
                    <th>KHO</th>
                    <th>TRẠNG THÁI</th>
                    <th></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="p" items="${stats.recentProducts}">
                    <tr>
                        <td class="prod-info">
                            <img class="admin-thumb"
                                 src="${p.imageUrl}"
                                 alt="${p.productName}">
                            <div>
                                <div class="fw">${p.productName}</div>
                                <div class="muted small-text">ID: ${p.id}</div>
                            </div>
                        </td>

                        <td>${p.categoryId}</td>
                        <td>${p.price}</td>
                        <td>${p.stock}</td>
                        <td>
                            <span class="${p.stock == 0 ? 'stock-out' : (p.stock < 10 ? 'stock-low' : 'stock-high')}">
                                    ${p.stock == 0 ? 'Hết' : (p.stock < 10 ? 'Ít' : 'Nhiều')}
                            </span>
                        </td>
                        <td class="text-right">
                            <a class="btn ghost" href="${pageContext.request.contextPath}/AdminProduct">Sửa</a>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty stats.recentProducts}">
                    <tr>
                        <td colspan="6" class="muted">Chưa có sản phẩm.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>

    </section>
</div>
<script>
    const revLabels = [
        <c:forEach var="l" items="${stats.revenueLabels}" varStatus="st">
        "${l}"<c:if test="${!st.last}">, </c:if>
        </c:forEach>
    ];
    const revValues = [
        <c:forEach var="v" items="${stats.revenueValues}" varStatus="st">
        ${v}<c:if test="${!st.last}">, </c:if>
        </c:forEach>
    ];

    new Chart(document.getElementById('revChart'), {
        type: 'line',
        data: {
            labels: revLabels,
            datasets: [{
                label: 'Doanh thu (VNĐ)',
                data: revValues,
                tension: 0.3
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {legend: {display: true}},
            scales: {y: {beginAtZero: true}}
        }
    });

    // ===== Order status chart =====
    const stLabels = [
        <c:forEach var="l" items="${stats.orderStatusLabels}" varStatus="st">
        "${l}"<c:if test="${!st.last}">, </c:if>
        </c:forEach>
    ];
    const stValues = [
        <c:forEach var="v" items="${stats.orderStatusValues}" varStatus="st">
        ${v}<c:if test="${!st.last}">, </c:if>
        </c:forEach>
    ];

    new Chart(document.getElementById('statusChart'), {
        type: 'doughnut',
        data: {
            labels: stLabels,
            datasets: [{
                data: stValues
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {legend: {position: 'bottom'}}
        }
    });
</script>
</body>
</html>
