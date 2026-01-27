<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Object.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tin tức & Sự kiện</title>

    <!-- CHỈ 1 CSS -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfNews&Event.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<!-- ================= HEADER ================= -->
<header class="site-header">
    <div class="header-inner">
        <a class="logo" href="${pageContext.request.contextPath}/Home">
            <img src="${pageContext.request.contextPath}/Images/Logo.jpg">
            Mắt kính Nông Lâm
        </a>

        <nav class="main-nav">
            <a href="${pageContext.request.contextPath}/Home">Trang chủ</a>
            <a href="${pageContext.request.contextPath}/About">Giới thiệu</a>
            <a href="${pageContext.request.contextPath}/Contact">Liên hệ</a>
        </nav>

        <div class="auth">
            <c:choose>
                <c:when test="${user == null}">
                    <a href="${pageContext.request.contextPath}/Login">Đăng nhập</a>
                    <a href="${pageContext.request.contextPath}/Register" class="btn">Đăng ký</a>
                </c:when>
                <c:otherwise>
                    <span>Xin chào, ${user.fullName}</span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>

<!-- ================= MAIN ================= -->
<main>

    <!-- ===== BANNER SỰ KIỆN ===== -->
    <section class="banner-section">
        <h2>Tin tức & Sự kiện</h2>

        <div class="banner-grid">
            <c:forEach items="${mainBanners}" var="b">
                <a href="${pageContext.request.contextPath}/PromotionDetail?id=${b.promotionId}">
                    <img src="${pageContext.request.contextPath}${b.imageUrl}">
                </a>
            </c:forEach>
        </div>
    </section>

    <!-- ===== DANH SÁCH KHUYẾN MÃI ===== -->
    <section class="promotion-section">
        <h2>Chương trình khuyến mãi</h2>

        <div class="promotion-grid">
            <c:forEach items="${promotions}" var="p">
                <div class="promotion-card">
                    <h3>${p.title}</h3>
                    <p>${p.content}</p>

                    <p class="time">
                        <fmt:formatDate value="${p.startDate}" pattern="dd/MM/yyyy"/>
                        -
                        <fmt:formatDate value="${p.endDate}" pattern="dd/MM/yyyy"/>
                    </p>

                    <p class="discount">
                        Giảm ${p.discountValue}%
                    </p>
                </div>
            </c:forEach>
        </div>
    </section>

    <!-- ===== FLASH SALE ===== -->
    <section class="flash-section">
        <h2>Flash Sale Cuối Tuần</h2>

        <div class="product-grid">
            <c:forEach items="${flashSaleProducts}" var="sp">
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}/${sp.thumbnail}">
                    <h4>${sp.name}</h4>

                    <p class="old-price">
                        <fmt:formatNumber value="${sp.price}" type="number"/> VNĐ
                    </p>

                    <p class="sale-price">
                        <fmt:formatNumber value="${sp.price * 30 / 100}" type="number"/> VNĐ
                    </p>
                </div>
            </c:forEach>
        </div>
    </section>

</main>

<!-- ================= FOOTER ================= -->
<footer class="site-footer">
    <p>© 2026 Mắt kính Nông Lâm</p>
</footer>

</body>
</html>
