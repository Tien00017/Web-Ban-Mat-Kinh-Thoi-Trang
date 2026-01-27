<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="Model.Object.User" %>
<%
    User user = (User) session.getAttribute("user");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết quả tìm kiếm</title>

    <!-- CSS dùng chung với trang chủ -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfSearchPage.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<!-- ================= HEADER (GIỐNG HOME) ================= -->
<header class="site-header">
    <div class="header-inner">
        <div class="header-left">
            <a class="logo" href="#">
                <img src="${pageContext.request.contextPath}/Images/Logo.jpg"
                     alt="Logo" class="logo-img">
                Mắt kính Nông Lâm
            </a>
            <nav class="main-nav" aria-label="Chính">
                <a href="${pageContext.request.contextPath}/Home" class="active">Trang chủ</a>
                <a href="${pageContext.request.contextPath}/About"> Giới thiệu </a>
                <a href="Contact.html">Liên hệ</a>
            </nav>
        </div>

        <div class="header-right">
            <form class="search-wrap"
                  action="${pageContext.request.contextPath}/Search"
                  method="get">

                <input type="search"
                       name="keyword"
                       placeholder="Tìm kiếm sản phẩm, mã..."
                       aria-label="Tìm kiếm"
                       required>

                <button class="search-btn" type="submit" aria-label="Tìm">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
                         fill="none" stroke="black" stroke-width="2"
                         stroke-linecap="round" stroke-linejoin="round" class="icon">
                        <circle cx="8" cy="8" r="6"/>
                        <line x1="18" y1="18" x2="13.65" y2="13.65"/>
                    </svg>
                </button>

            </form>

            <div class="header-icons">
                <% if (user == null) { %>
                <!-- Chưa đăng nhập: chỉ hiện Đăng nhập / Đăng ký -->
                <a class="btn-outline" href="${pageContext.request.contextPath}/Login">Đăng nhập</a>
                <a class="btn-primary" href="${pageContext.request.contextPath}/Register">Đăng ký</a>
                <% } else { %>
                <!-- Đã đăng nhập: hiện Cart, Profile, tên người dùng và Đăng xuất -->
                <a href="${pageContext.request.contextPath}/Cart" aria-label="Giỏ hàng">
                    <button class="icon-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="black"
                             stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon">
                            <circle cx="9" cy="21" r="1"/>
                            <circle cx="20" cy="21" r="1"/>
                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                        </svg>
                    </button>
                </a>
                <a href="${pageContext.request.contextPath}/Profile" aria-label="Thông tin">
                    <button class="icon-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none"
                             stroke="black" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                             class="icon">
                            <path d="M20 21v-2a4 4 0 0 0-3-3.87"/>
                            <path d="M4 21v-2a4 4 0 0 1 3-3.87"/>
                            <circle cx="12" cy="7" r="4"/>
                        </svg>
                    </button>
                </a>
                <% } %>
            </div>
        </div>
    </div>

    <!-- CATEGORY BAR -->
    <nav class="category-bar">
        <a href="${pageContext.request.contextPath}/Kinh_Can" class="cat">Kính Cận</a>
        <a href="${pageContext.request.contextPath}/Kinh_Mat" class="cat">Kính Mát</a>
        <a href="${pageContext.request.contextPath}/Kinh_Ap_Trong" class="cat">Kính Áp Tròng</a>
        <a href="${pageContext.request.contextPath}/Gong_Kinh" class="cat">Gọng Kính</a>
    </nav>
</header>

<!-- ================= MAIN ================= -->
<main class="container">

    <!-- TIÊU ĐỀ -->
    <section class="news-section">
        <h2>
            Kết quả tìm kiếm cho:
            <span style="color:#1e88e5;">"${keyword}"</span>
        </h2>
    </section>

    <!-- ================= SẢN PHẨM ================= -->
    <section class="featured">
        <h2>Sản phẩm</h2>

        <c:if test="${empty products}">
            <p>❌ Không tìm thấy sản phẩm phù hợp.</p>
        </c:if>

        <div class="product-grid">
            <c:forEach items="${products}" var="p">
                <div class="product-card">
                    <a href="${pageContext.request.contextPath}/ProductDetail?id=${p.id}">

                        <c:if test="${not empty productImages[p.id]}">
                            <img src="${productImages[p.id].imageUrl}">
                        </c:if>

                        <h4>${p.productName}</h4>
                    </a>
                </div>
            </c:forEach>
        </div>

    </section>

<%--    <!-- ================= TIN TỨC / SỰ KIỆN ================= -->--%>
<%--    <section class="news-section">--%>
<%--        <h2>Tin tức & Sự kiện</h2>--%>

<%--        <c:if test="${empty newsList}">--%>
<%--            <p>❌ Không tìm thấy tin tức phù hợp.</p>--%>
<%--        </c:if>--%>

<%--        <div class="news-track">--%>
<%--            <c:forEach items="${newsList}" var="n">--%>
<%--                <article class="news-card">--%>
<%--                    <a href="${pageContext.request.contextPath}/NewsDetail?id=${n.id}">--%>
<%--                        <img src="${pageContext.request.contextPath}/${n.image}">--%>
<%--                        <h4 style="padding:10px">${n.title}</h4>--%>
<%--                    </a>--%>
<%--                </article>--%>
<%--            </c:forEach>--%>
<%--        </div>--%>
<%--    </section>--%>

</main>

<!-- ================= FOOTER (GIỐNG HOME) ================= -->
<footer class="site-footer">
    <div class="footer-inner">

        <!-- Chi nhánh -->
        <div class="footer-branches">
            <h4>Các chi nhánh</h4>
            <p>Chi nhánh Hà Nội: 123 Phố Huế, Hoàn Kiếm</p>
            <p>Chi nhánh TP.HCM: 456 Nguyễn Huệ, Quận 1</p>
            <p>Chi nhánh Đà Nẵng: 789 Bạch Đằng, Hải Châu</p>
        </div>

        <!-- Liên hệ -->
        <div class="footer-contact">
            <h4>Liên hệ</h4>
            <p><i class="fas fa-envelope"></i> Email: support@example.com</p>
            <p><i class="fas fa-phone"></i> Điện thoại: 0123 456 789</p>
        </div>

        <!-- Theo dõi -->
        <div class="footer-social">
            <h4>Theo dõi</h4>
            <p class="social-icons">
                <a href="#"><i class="fab fa-facebook-f"></i></a>
                <a href="#"><i class="fab fa-instagram"></i></a>
                <a href="#"><i class="fab fa-zalo"></i></a>
            </p>
        </div>

    </div>
</footer>

</body>
</html>
