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
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Mắt kính Nông Lâm - Trang profile</title>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfCart.css">
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

<main>
    <div class="cart-wrapper">

        <!-- LEFT: CART LIST -->
        <div id="cartList" class="cart-list">

            <c:if test="${empty cart || empty cart.cartItems}">
                <p>Giỏ hàng của bạn đang trống</p>
            </c:if>

            <c:forEach var="item" items="${cart.cartItems.values()}">
                <div class="cart-item">

                    <label class="checkbox">
                        <input type="checkbox" class="row-check" checked>
                    </label>

                    <div class="thumb">
                        <img src="${item.image}" alt="${item.name}">
                    </div>

                    <div class="info">

                        <div class="row-1">
                            <div class="meta">
                                <div>
                                    <div>
                                        <span class="label">Tên sản phẩm:</span>
                                    </div>
                                    <div>
                                        <span class="val">${item.name}</span>
                                    </div>
                                </div>
                            </div>

                            <!-- FORM XÓA -->
                            <div class="row-links">
                                <form action="${pageContext.request.contextPath}/Cart" method="post">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="productId" value="${item.productId}">
                                    <button class="link danger js-delete" type="submit">Xóa</button>
                                </form>
                            </div>
                        </div>

                        <div class="row-2">
                            <div class="price js-price">
                                <span class="label">Giá:</span>
                                <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> VNĐ
                            </div>

                            <div class="qty">
                                <span class="label">Số lượng:</span>

                                <div class="qty-ctl">

                                    <!-- GIẢM -->
                                    <form action="${pageContext.request.contextPath}/Cart" method="post">
                                        <input type="hidden" name="action" value="decrease">
                                        <input type="hidden" name="productId" value="${item.productId}">
                                        <button class="qty-btn js-dec" type="submit">−</button>
                                    </form>

                                    <input class="js-qty" type="number" value="${item.quantity}" readonly>

                                    <!-- TĂNG -->
                                    <form action="${pageContext.request.contextPath}/Cart" method="post">
                                        <input type="hidden" name="action" value="increase">
                                        <input type="hidden" name="productId" value="${item.productId}">
                                        <button class="qty-btn js-inc" type="submit">+</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </c:forEach>
        </div>

        <aside class="summary-box">
            <h2 class="title">Tổng đơn hàng</h2>

            <div class="summary-row">
                <span>Số lượng sản phẩm</span>
                <span class="count">${totalQty}</span>
            </div>

            <hr>

            <div class="summary-row total">
                <span>Thành tiền</span>
                <strong class="val">
                    <fmt:formatNumber value="${totalPrice}" type="number" groupingUsed="true"/> VNĐ
                </strong>
            </div>

            <a href="Checkout.html" class="checkout-btn">Thanh Toán</a>

            <a href="${pageContext.request.contextPath}/Home" class="continue">Tiếp tục mua hàng</a>
        </aside>
    </div>
</main>

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
<script src="${pageContext.request.contextPath}/JavaScript/Cart.js" defer></script>
</body>
</html>