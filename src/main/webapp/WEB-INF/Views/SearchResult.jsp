<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="Model.Object.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết quả tìm kiếm</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfSearchPage.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<!-- ================= HEADER ================= -->
<header class="site-header">
    <div class="header-inner">
        <div class="header-left">
            <a class="logo" href="${pageContext.request.contextPath}/Home">
                <img src="${pageContext.request.contextPath}/Images/Logo.jpg" class="logo-img">
                Mắt kính Nông Lâm
            </a>

            <nav class="main-nav">
                <a href="${pageContext.request.contextPath}/Home">Trang chủ</a>
                <a href="${pageContext.request.contextPath}/About">Giới thiệu</a>
                <a href="#">Liên hệ</a>
            </nav>
        </div>

        <div class="header-right">
            <form class="search-wrap"
                  action="${pageContext.request.contextPath}/Search"
                  method="get">
                <input type="search" name="keyword"
                       placeholder="Tìm kiếm sản phẩm..."
                       required>
                <button type="submit"><i class="fa fa-search"></i></button>
            </form>

            <div class="header-icons">
                <% if (user == null) { %>
                    <a class="btn-outline" href="${pageContext.request.contextPath}/Login">Đăng nhập</a>
                    <a class="btn-primary" href="${pageContext.request.contextPath}/Register">Đăng ký</a>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/Cart"><i class="fa fa-cart-shopping"></i></a>
                    <a href="${pageContext.request.contextPath}/Profile"><i class="fa fa-user"></i></a>
                <% } %>
            </div>
        </div>
    </div>
</header>

<!-- ================= MAIN ================= -->
<main class="container">

    <h2 class="search-title">
        Kết quả tìm kiếm cho:
        <span>"<c:out value='${keyword}'/>"</span>
    </h2>

    <c:if test="${empty products}">
        <p class="empty-text">❌ Không tìm thấy sản phẩm phù hợp</p>
    </c:if>

    <div class="product-grid">
        <c:forEach items="${products}" var="p">
            <div class="product-card">
                <a href="${pageContext.request.contextPath}/ProductDetail?id=${p.id}">
                    <c:choose>
                        <c:when test="${productImages[p.id] != null}">
                            <img src="${productImages[p.id].imageUrl}">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/Images/no-image.png">
                        </c:otherwise>
                    </c:choose>
                    <h4>${p.productName}</h4>
                </a>
            </div>
        </c:forEach>
    </div>

</main>

<!-- ================= FOOTER ================= -->
<footer class="site-footer">
    <p>© 2026 Mắt kính Nông Lâm</p>
</footer>

</body>
</html>
