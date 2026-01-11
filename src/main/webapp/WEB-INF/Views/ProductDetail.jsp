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
    <title>Chi tiết sản phẩm</title>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfProductDetail.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
          integrity="sha512-..." crossorigin="anonymous" referrerpolicy="no-referrer"/>

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
                <a href="Contact.html">Liên hệ</a>
            </nav>
        </div>

        <div class="header-right">
            <div class="search-wrap">
                <input type="search" placeholder="Tìm kiếm sản phẩm, mã..." aria-label="Tìm kiếm">
                <button class="search-btn" aria-label="Tìm">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="none" stroke="black"
                         stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="icon">
                        <circle cx="8" cy="8" r="6"/>
                        <line x1="18" y1="18" x2="13.65" y2="13.65"/>
                    </svg>
                </button>
            </div>

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

<section class="product-detail">
    <!-- Ảnh sản phẩm bên trái-->
    <div class="product-images">
        <div class="thumb-list">
            <c:forEach var="img" items="${images}">
                <img src="${img.imageUrl}">
            </c:forEach>
        </div>

        <!-- Main image bên phải -->
        <div class="main-image">
            <c:if test="${not empty images}">
                <img src="${images[0].imageUrl}">
            </c:if>
        </div>
    </div>

    <!--Thông tin -->
    <div class="product-info">

        <h1 class="product-name">${product.productName}</h1>

        <p class="product-price">
            Giá: <strong>
            <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/> VNĐ
        </strong>
        </p>

        <div class="rating-box">
            <span class="stars">
                ⭐ ${reviewStats[1]}
            </span>
            <span class="rating-count">
                (${reviewStats[0]} đánh giá)
            </span>
        </div>

        <div class="action-buttons">
            <form action="${pageContext.request.contextPath}/Cart" method="post">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="${product.id}">
                <button type="submit" class="btn add big">
                    Thêm vào giỏ hàng
                </button>
            </form>
        </div>
    </div>
</section>
<section>

    <!-- tab menu-->
    <div class="tabs-wrapper">
        <div class="tabs">
            <div class="tab active" data-tab="detail">Chi tiết sản phẩm</div>
            <div class="tab" data-tab="description">Mô tả sản phẩm</div>
            <div class="tab" data-tab="guarantee">Bảo hành</div>
            <div class="tab" data-tab="ship">Vận chuyển & Trả lại</div>
        </div>
    </div>

    <!-- chi tiết sản phẩm-->
    <div class="tab-content active" id="detail">
        <div class="product-specs">
            <h2>Chi tiết sản phẩm</h2>

            <div class="spec-layout">

                <!--thông số -->
                <div class="spec-list">
                    <div class="spec-item"><strong>Tên:</strong> ${product.productName}</div>
                    <div class="spec-item"><strong>Thương hiệu:</strong> ${product.brand}</div>
                    <div class="spec-item"><strong>Xuất xứ:</strong> ${product.origin}</div>
                    <div class="spec-item"><strong>Giá:</strong>
                        <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/> VNĐ
                    </div>
                </div>

            </div>
        </div>
    </div>

    <!-- mô tả sản phẩm-->
    <div class="tab-content" id="description">
        <div class="description">
            <h2>Mô tả sản phẩm</h2>
            <p>${product.generalDescription}</p>
        </div>
    </div>

    <!-- Bảo hành-->
    <div class="tab-content" id="guarantee">
        <div class="guarantee">
            <h2>Bảo hành</h2>
            <p style="padding: 20px;">
                ${product.guaranteeDetails}
            </p>
        </div>
    </div>

    <!-- vận chuyển và trả lại-->
    <div class="tab-content" id="ship">
        <div class="ship">
            <h2>Vận chuyển và trả lại</h2>
            <p style="padding: 20px;">
                ${product.shippingInfo}
            </p>
        </div>
    </div>

</section>
<!-- BÌNH LUẬN SẢN PHẨM -->
<c:if test="${user != null}">
<section class="comment-section">
    <h2>Bình luận sản phẩm</h2>

        <form action="${pageContext.request.contextPath}/AddReview"
              method="post"
              class="comment-form">

            <!-- bắt buộc: productId -->
            <input type="hidden" name="productId" value="${product.id}">

            <!-- Rating -->
            <div class="rating-input">
                <div class="stars">
                    <i data-value="1">★</i>
                    <i data-value="2">★</i>
                    <i data-value="3">★</i>
                    <i data-value="4">★</i>
                    <i data-value="5">★</i>
                </div>
                <span class="rating-text">0 sao</span>

                <!-- gửi về server -->
                <input type="hidden" name="rating" id="ratingValue" required>
            </div>

            <!-- Comment -->
            <textarea name="comment"
                      placeholder="Viết bình luận của bạn..."
                      rows="3"
                      required></textarea>

            <button type="submit">Gửi bình luận</button>
        </form>
    </section>
</c:if>
<!--rating-->
<section class="rating-section">
    <h2 class="rating-title">Đánh giá & Nhận xét sản phẩm</h2>

    <div class="rating-summary">
        <div class="rating-score">
            ${reviewStats[1]}
        </div>

        <div class="rating-stars">
            <c:forEach begin="1" end="5" var="i">
                <c:choose>
                    <c:when test="${i <= reviewStats[1]}">
                        <i class="fa-solid fa-star"></i>
                    </c:when>
                    <c:when test="${i - reviewStats[1] <= 0.5}">
                        <i class="fa-solid fa-star-half-stroke"></i>
                    </c:when>
                    <c:otherwise>
                        <i class="fa-regular fa-star"></i>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

        <div class="count">
            (${reviewStats[0]} đánh giá)
        </div>
    </div>

    <div id="reviewList" class="review-list">

        <!-- REVIEW ITEM -->
        <c:forEach var="entry" items="${reviews}">
            <article class="review-item">
                <div class="r-user">${entry.key.fullName}</div>
                <div class="r-date">${entry.value.createdAt}</div>
                <div class="r-stars">⭐ ${entry.value.rating}</div>
                <div class="r-body">${entry.value.comment}</div>
            </article>
        </c:forEach>
    </div>
</section>

<section class="reco-section">
    <h2>Sản phẩm tương tự</h2>

    <div class="reco-grid">
        <c:forEach var="p" items="${relatedProducts}">
            <article class="reco-card">

                <!-- IMAGE -->
                <c:set var="img" value="${relatedImageMap[p.id]}"/>
                <c:choose>
                    <c:when test="${img != null}">
                        <img class="reco-img"
                             src="${img.imageUrl}"
                             alt="${p.productName}">
                    </c:when>
                    <c:otherwise>
                        <img class="reco-img"
                             src="${pageContext.request.contextPath}/Images/no-image.png"
                             alt="No image">
                    </c:otherwise>
                </c:choose>

                <!-- INFO -->
                <div class="reco-info">
                    <div class="reco-name">${p.productName}</div>

                    <div class="reco-price">
                        <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/> VNĐ
                    </div>

                    <div class="reco-sub">
                            ${p.brand}
                    </div>

                    <a href="${pageContext.request.contextPath}/ProductDetail?id=${p.id}"
                       class="btn add js-add">
                        Xem sản phẩm
                    </a>
                </div>

            </article>
        </c:forEach>
    </div>
</section>


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
<button id="backToTop" aria-label="Lên đầu trang"><i class="fa-solid fa-up-long"></i></button>
<script src="${pageContext.request.contextPath}/JavaScript/ProductDetail.js"></script>
</body>
</html>