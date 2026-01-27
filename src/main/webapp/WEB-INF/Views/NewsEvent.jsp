<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Object.User" %>

<%
    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Mắt kính Nông Lâm - Tin tức & Sự kiện</title>

    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfHomePage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfNews&Event.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>

<body>

<!-- ================= HEADER ================= -->
<header class="site-header">
    <div class="header-inner">
        <div class="header-left">
            <a class="logo" href="${pageContext.request.contextPath}/HomePage">
                <img src="${pageContext.request.contextPath}/Images/Logo.jpg" class="logo-img">
                Mắt kính Nông Lâm
            </a>

            <nav class="main-nav">
                <a href="${pageContext.request.contextPath}/HomePage">Trang chủ</a>
                <a href="${pageContext.request.contextPath}/AboutUs">Giới thiệu</a>
                <a href="${pageContext.request.contextPath}/Contact">Liên hệ</a>
                <a href="${pageContext.request.contextPath}/NewsEvent" class="active">Tin tức & Sự kiện</a>
            </nav>
        </div>

        <div class="header-right">
            <form class="search-wrap" action="${pageContext.request.contextPath}/Search" method="get">
                <input type="search" name="keyword" placeholder="Tìm kiếm sản phẩm, mã...">
                <button class="search-btn"><i class="fas fa-search"></i></button>
            </form>

            <div class="header-icons">
                <a href="${pageContext.request.contextPath}/Cart" class="icon-btn">
                    <i class="fas fa-shopping-cart"></i>
                </a>

                <a href="${pageContext.request.contextPath}/Profile" class="icon-btn">
                    <i class="fas fa-user"></i>
                </a>

                <% if (user == null) { %>
                    <a class="btn-outline" href="${pageContext.request.contextPath}/Login">Đăng nhập</a>
                    <a class="btn-primary" href="${pageContext.request.contextPath}/Register">Đăng ký</a>
                <% } else { %>
                    <span class="hello-user">Xin chào, <%= user.getFullName() %></span>
                    <a class="btn-outline" href="${pageContext.request.contextPath}/Logout">Đăng xuất</a>
                <% } %>
            </div>
        </div>
    </div>

    <nav class="category-bar">
        <a href="${pageContext.request.contextPath}/Kinh_Can">Kính Cận</a>
        <a href="${pageContext.request.contextPath}/Kinh_Mat">Kính Mát</a>
        <a href="${pageContext.request.contextPath}/Kinh_Ap_Trong">Kính Áp Tròng</a>
        <a href="${pageContext.request.contextPath}/Gong_Kinh">Gọng Kính</a>
    </nav>
</header>

<!-- ================= MAIN ================= -->
<main class="container">

    <!-- ===== EVENT 1: FLASH SALE ===== -->
    <section class="flash-banner" id="event-flash">
        <div class="flash-banner-inner">
            <h1>FLASH SALE – GIẢM SỐC CUỐI TUẦN</h1>
            <p>Giảm đến 70% – Số lượng có hạn</p>

            <div class="flash-countdown" id="countdown">
                <span id="days">00</span> :
                <span id="hours">00</span> :
                <span id="minutes">00</span> :
                <span id="seconds">00</span>
            </div>

            <a class="btn-primary flash-btn" href="#flash-items">Xem ngay</a>
        </div>
    </section>

    <section class="flash-items" id="flash-items">
        <h2>Flash Sale Hot Nhất</h2>

        <div class="product-grid flash-grid">
            <div class="product-card flash-card">
                <img src="${pageContext.request.contextPath}/Images/HomePage/GongKinh1.png">
                <h4>Kính A</h4>
                <p class="price-old">1.200.000 VNĐ</p>
                <p class="price-sale">299.000 VNĐ</p>
            </div>

            <div class="product-card flash-card">
                <img src="${pageContext.request.contextPath}/Images/HomePage/KinhCan2.png">
                <h4>Kính B</h4>
                <p class="price-old">950.000 VNĐ</p>
                <p class="price-sale">249.000 VNĐ</p>
            </div>

            <div class="product-card flash-card">
                <img src="${pageContext.request.contextPath}/Images/HomePage/GongKinh2.jpg">
                <h4>Kính C</h4>
                <p class="price-old">1.500.000 VNĐ</p>
                <p class="price-sale">399.000 VNĐ</p>
            </div>
        </div>
    </section>

    <!-- ===== EVENT 2: TẾT ===== -->
    <section class="event-section" id="event-tet">
        <h1>ƯU ĐÃI TẾT – SẮM KÍNH ĐÓN XUÂN</h1>
        <p>Giảm <strong>30%</strong> cho kính cận, gọng kính & combo tròng kính</p>
        <ul class="event-info">
            <li>🎁 Miễn phí đo mắt</li>
            <li>🎁 Tặng khăn lau kính cao cấp</li>
            <li>🎁 Bảo hành tròng kính 6 tháng</li>
        </ul>
    </section>

    <!-- ===== EVENT 3: SUMMER SALE ===== -->
    <section class="event-section" id="event-summer">
        <h1>SUMMER SALE – BẢO VỆ MẮT MÙA NẮNG</h1>
        <p>Giảm <strong>40%</strong> cho kính mát & kính chống tia UV</p>

        <div class="product-grid">
            <div class="product-card">
                <img src="${pageContext.request.contextPath}/Images/HomePage/KinhMat1.jpg">
                <h4>Kính mát A</h4>
                <p class="price">499.000 VNĐ</p>
            </div>

            <div class="product-card">
                <img src="${pageContext.request.contextPath}/Images/HomePage/KinhMat2.jpg">
                <h4>Kính mát B</h4>
                <p class="price">699.000 VNĐ</p>
            </div>
        </div>
    </section>

</main>

<!-- ================= FOOTER ================= -->
<footer class="site-footer">
    <div class="footer-inner">
        <div class="footer-branches">
            <h4>Các chi nhánh</h4>
            <p>Hà Nội – TP.HCM – Đà Nẵng</p>
        </div>

        <div class="footer-contact">
            <h4>Liên hệ</h4>
            <p>Email: support@example.com</p>
            <p>Hotline: 0123 456 789</p>
        </div>

        <div class="footer-social">
            <a href="#"><i class="fab fa-facebook-f"></i></a>
            <a href="#"><i class="fab fa-instagram"></i></a>
            <a href="#"><i class="fab fa-zalo"></i></a>
        </div>
    </div>
</footer>

<script src="${pageContext.request.contextPath}/JavaScript/FlashSale.js"></script>
</body>
</html>
