<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="Model.Object.User" %>
<%
    User user = (User) session.getAttribute("user");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kính Mát - Mắt Kinh Nông Lâm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfKinh_Mat.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
          integrity="sha512-..." crossorigin="anonymous" referrerpolicy="no-referrer"/>

    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
</head>

<body>
<!-- HEADER -->
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
                <a href="${pageContext.request.contextPath}/About">Giới thiệu</a>
                <a href="${pageContext.request.contextPath}/Contact">Liên hệ</a>
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
        <a href="${pageContext.request.contextPath}/Kinh_Mat" class="cat active">Kính Mát</a>
        <a href="${pageContext.request.contextPath}/Kinh_Ap_Trong" class="cat">Kính Áp Tròng</a>
        <a href="${pageContext.request.contextPath}/Gong_Kinh" class="cat">Gọng Kính</a>
    </nav>
</header>

<div class="container">

    <form method="get"
          action="${pageContext.request.contextPath}/Kinh_Mat"
          class="sidebar">
        <input type="hidden" name="page" value="${currentPage != null ? currentPage : 1}">

        <h3>Lọc</h3>

        <h4>Chất liệu</h4>
        <label>
            <input type="radio" name="material" value="Kim loại"
                   <c:if test="${param.material == 'Kim loại'}">checked</c:if> >
            Kim loại
        </label>
        <label>
            <input type="radio" name="material" value="Nhựa"
                   <c:if test="${param.material == 'Nhựa'}">checked</c:if> >
            Nhựa
        </label>
        <label>
            <input type="radio" name="material" value="Titan"
                   <c:if test="${param.material == 'Titan'}">checked</c:if> >
            Titan
        </label>

        <h4>Hình dáng</h4>
        <label>
            <input type="radio" name="shape" value="Vuông"
                   <c:if test="${param.shape == 'Vuông'}">checked</c:if> >
            Vuông
        </label>
        <label>
            <input type="radio" name="shape" value="Tròn"
                   <c:if test="${param.shape == 'Tròn'}">checked</c:if> >
            Tròn
        </label>
        <label>
            <input type="radio" name="shape" value="Oval"
                   <c:if test="${param.shape == 'Oval'}">checked</c:if> >
            Oval
        </label>
        <label>
            <input type="radio" name="shape" value="Mắt mèo"
                   <c:if test="${param.shape == 'Mắt mèo'}">checked</c:if> >
            Mắt mèo
        </label>

        <h4>Giá</h4>
        <label>
            <input type="radio" name="priceRange" value="low"
                   <c:if test="${param.priceRange == 'low'}">checked</c:if> >
            Dưới 500k
        </label>
        <label>
            <input type="radio" name="priceRange" value="mid"
                   <c:if test="${param.priceRange == 'mid'}">checked</c:if> >
            500k - 1tr
        </label>
        <label>
            <input type="radio" name="priceRange" value="high"
                   <c:if test="${param.priceRange == 'high'}">checked</c:if> >
            Trên 1tr
        </label>

        <h4>Sắp xếp theo giá</h4>
        <label>
            <input type="radio" name="sortPrice" value="asc"
                   <c:if test="${param.sortPrice == 'asc'}">checked</c:if> >
            Thấp → cao
        </label>
        <label>
            <input type="radio" name="sortPrice" value="desc"
                   <c:if test="${param.sortPrice == 'desc'}">checked</c:if> >
            Cao → thấp
        </label>

        <button type="submit" class="btn">Lọc</button>
    </form>

    <section class="products">
        <div class="product-grid">
            <c:forEach var="p" items="${products}">
                <div class="product-card">

                    <c:set var="img" value="${imageMap[p.id]}"/>

                    <c:choose>
                        <c:when test="${img != null}">
                            <img src="${img.imageUrl}" alt="${p.productName}">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/Images/no-image.png"
                                 alt="No image">
                        </c:otherwise>
                    </c:choose>

                    <h4>${p.productName}</h4>

                    <div class="price-box">
                        <c:choose>

                            <%-- CÓ GIẢM GIÁ --%>
                            <c:when test="${salePriceMap[p.id] != null}">
                                <p class="price-old">
                                    <del>
                                        <fmt:formatNumber value="${p.price}"
                                                          type="number"
                                                          groupingUsed="true"/> VNĐ
                                    </del>
                                </p>
                                <p class="price-sale">
                                    <fmt:formatNumber value="${salePriceMap[p.id]}"
                                                      type="number"
                                                      groupingUsed="true"/> VNĐ
                                </p>
                            </c:when>

                            <%-- KHÔNG GIẢM GIÁ --%>
                            <c:otherwise>
                                <p class="price">
                                    <fmt:formatNumber value="${p.price}"
                                                      type="number"
                                                      groupingUsed="true"/> VNĐ
                                </p>
                            </c:otherwise>

                        </c:choose>
                    </div>

                    <a href="${pageContext.request.contextPath}/ProductDetail?id=${p.id}"
                       class="try-btn">
                        Xem sản phẩm
                    </a>
                </div>
            </c:forEach>

        </div>
        <div class="pagination">
            <c:if test="${totalPages > 1}">
                <%
                    int maxDisplay = 3; // số trang hiển thị tối đa
                    int currentPage = (Integer) request.getAttribute("currentPage");
                    int totalPages = (Integer) request.getAttribute("totalPages");

                    int start = Math.max(1, currentPage - 2);
                    int end = Math.min(totalPages, start + maxDisplay - 1);
                    if (end - start + 1 < maxDisplay) {
                        start = Math.max(1, end - maxDisplay + 1);
                    }
                %>

                <!-- Nút trang đầu tiên << -->
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/Kinh_Mat?material=${param.material}&shape=${param.shape}&priceRange=${param.priceRange}&sortPrice=${param.sortPrice}&page=1"
                       class="page-nav">&lt;&lt;</a>
                </c:if>

                <!-- Nút trang trước -->
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/Kinh_Mat?material=${param.material}&shape=${param.shape}&priceRange=${param.priceRange}&sortPrice=${param.sortPrice}&page=${currentPage - 1}"
                       class="page-nav">&lt;</a>
                </c:if>

                <!-- Trang đầu nếu start > 1 -->
                <c:if test="${start > 1}">
                    <a href="${pageContext.request.contextPath}/Kinh_Mat?material=${param.material}&shape=${param.shape}&priceRange=${param.priceRange}&sortPrice=${param.sortPrice}&page=1"
                       class="page">1</a>
                    <span class="dots">…</span>
                </c:if>

                <!-- Các trang hiển thị -->
                <c:forEach begin="<%=start%>" end="<%=end%>" var="i">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <span class="page current">${i}</span>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/Kinh_Mat?material=${param.material}&shape=${param.shape}&priceRange=${param.priceRange}&sortPrice=${param.sortPrice}&page=${i}"
                               class="page">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <!-- Trang cuối nếu end < totalPages -->
                <c:if test="${end < totalPages}">
                    <span class="dots">…</span>
                    <a href="${pageContext.request.contextPath}/Kinh_Mat?material=${param.material}&shape=${param.shape}&priceRange=${param.priceRange}&sortPrice=${param.sortPrice}&page=${totalPages}"
                       class="page">${totalPages}</a>
                </c:if>

                <!-- Nút trang tiếp theo -->
                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/Kinh_Mat?material=${param.material}&shape=${param.shape}&priceRange=${param.priceRange}&sortPrice=${param.sortPrice}&page=${currentPage + 1}"
                       class="page-nav">&gt;</a>
                </c:if>

                <!-- Nút trang cuối >> -->
                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/Kinh_Mat?material=${param.material}&shape=${param.shape}&priceRange=${param.priceRange}&sortPrice=${param.sortPrice}&page=${totalPages}"
                       class="page-nav">&gt;&gt;</a>
                </c:if>

                <!-- Dòng trạng thái -->
                <div class="page-status">
                    Trang ${currentPage} / ${totalPages}
                </div>
            </c:if>
        </div>
    </section>

</div>
<!-- FOOTER -->
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
<script src="${pageContext.request.contextPath}/JavaScript/Filter.js"></script>
</body>
</html>
