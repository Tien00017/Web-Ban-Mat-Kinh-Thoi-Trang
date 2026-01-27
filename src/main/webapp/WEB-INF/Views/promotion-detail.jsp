<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Object.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>${promotion.title}</title>

    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfHomePage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfNews&Event.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
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
                <a class="active">Tin tức & Sự kiện</a>
            </nav>
        </div>

        <div class="header-right">
            <form class="search-wrap" action="${pageContext.request.contextPath}/Search">
                <input type="search" name="keyword" placeholder="Tìm kiếm sản phẩm...">
                <button class="search-btn"><i class="fas fa-search"></i></button>
            </form>

            <div class="header-icons">
                <a href="${pageContext.request.contextPath}/Cart" class="icon-btn">
                    <i class="fas fa-shopping-cart"></i>
                </a>
                <a href="${pageContext.request.contextPath}/Profile" class="icon-btn">
                    <i class="fas fa-user"></i>
                </a>

                <c:choose>
                    <c:when test="${user == null}">
                        <a class="btn-outline" href="${pageContext.request.contextPath}/Login">Đăng nhập</a>
                        <a class="btn-primary" href="${pageContext.request.contextPath}/Register">Đăng ký</a>
                    </c:when>
                    <c:otherwise>
                        <span class="hello-user">Xin chào, ${user.fullName}</span>
                        <a class="btn-outline" href="${pageContext.request.contextPath}/Logout">Đăng xuất</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</header>

<!-- ================= MAIN ================= -->
<main class="container">

    <!-- PROMOTION BANNER -->
    <section class="flash-banner"
             style="background-image:url('${mainBanner.imageUrl}')">
        <div class="flash-banner-inner">
            <h1>${promotion.title}</h1>
            <p>${promotion.content}</p>

            <div class="flash-countdown" id="countdown">
                <span id="days">00</span> :
                <span id="hours">00</span> :
                <span id="minutes">00</span> :
                <span id="seconds">00</span>
            </div>

            <a class="btn-primary flash-btn" href="#flash-items">Xem ngay</a>
        </div>
    </section>

    <!-- PRODUCT LIST -->
    <section class="flash-items" id="flash-items">
        <h2>Sản phẩm đang khuyến mãi</h2>

        <div class="product-grid flash-grid">
            <c:forEach var="p" items="${products}">
                <div class="product-card flash-card">
                    <a href="${pageContext.request.contextPath}/ProductDetail?id=${p.id}">
                        <img src="${p.image}">
                        <h4>${p.name}</h4>

                        <p class="price-old">
                            <fmt:formatNumber value="${p.price}" type="number"/> VNĐ
                        </p>

                        <p class="price-sale">
                            <fmt:formatNumber
                                value="${p.price * (1 - promotion.discountValue / 100)}"
                                type="number"/> VNĐ
                        </p>

                        <div class="buy-now">Mua ngay</div>
                    </a>
                </div>
            </c:forEach>

            <c:if test="${empty products}">
                <p>Hiện chưa có sản phẩm cho chương trình này.</p>
            </c:if>
        </div>
    </section>

</main>

<!-- ================= FOOTER ================= -->
<footer class="site-footer">
    <div class="footer-inner">
        <div>
            <h4>Chi nhánh</h4>
            <p>Hà Nội – TP.HCM – Đà Nẵng</p>
        </div>
        <div>
            <h4>Liên hệ</h4>
            <p>Email: support@example.com</p>
            <p>Hotline: 0123 456 789</p>
        </div>
    </div>
</footer>

<script src="${pageContext.request.contextPath}/JavaScript/FlashSale.js"></script>
</body>
</html>
