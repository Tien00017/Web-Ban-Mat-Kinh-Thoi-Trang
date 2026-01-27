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
    <title>Thanh toán</title>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfHomePage.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfCheckout.css">
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
                <a href="${pageContext.request.contextPath}/Contact">Liên hệ</a>
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

<section class="checkout-wrapper">
    <div class="checkout-container">

        <form action="${pageContext.request.contextPath}/Checkout" method="post">
            <div class="checkout-left">
                <h2>Thông tin thanh toán</h2>

                <div class="form-group">
                    <label>Họ và tên</label>
                    <input type="text"
                           name="fullName"
                           placeholder="Nhập họ tên"
                           value="<c:out value='${user.fullName}'/>"
                           required>
                </div>

                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="tel"
                           name="phone"
                           placeholder="Nhập số điện thoại"
                           value="<c:out value='${user.phone}'/>"
                           required>
                </div>

                <div class="form-group">
                    <label>Địa chỉ giao hàng</label>
                    <input type="text"
                           name="address"
                           placeholder="Nhập địa chỉ"
                           value="<c:out value='${user.address}'/>"
                           required>
                </div>

                <div class="payment-box">
                    <h3>Phương thức thanh toán</h3>

                    <label class="payment-option">
                        <input type="radio" name="pay" checked>
                        <span>Thanh toán khi giao hàng (COD)</span>
                    </label>

<%--                    <label class="payment-option">--%>
<%--                        <input type="radio" name="pay">--%>
<%--                        <span>Chuyển khoản qua ngân hàng</span>--%>
<%--                    </label>--%>

<%--                    <label class="payment-option">--%>
<%--                        <input type="radio" name="pay">--%>
<%--                        <span>--%>
<%--                        Thanh toán qua cổng VNPAY (ATM / Visa / MasterCard / JCB / QR Pay)--%>
<%--                    </span>--%>
<%--                    </label>--%>

<%--                    <label class="payment-option">--%>
<%--                        <input type="radio" name="pay">--%>
<%--                        <span>Thanh toán qua ví MoMo</span>--%>
<%--                    </label>--%>

<%--                    <label class="payment-option">--%>
<%--                        <input type="radio" name="pay">--%>
<%--                        <span>Thanh toán qua ZaloPay</span>--%>
<%--                    </label>--%>
                </div>

                <button type="submit" class="btn-order">
                    Đặt hàng
                </button>
            </div>
        </form>
        <div class="checkout-right">
            <h3>Tóm tắt đơn hàng</h3>

            <div class="summary-list">
                <c:forEach var="item" items="${cart.cartItems.values()}">
                    <div class="summary-item">
                        <img src="${item.image}" alt="${item.name}">
                        <div>
                            <p>${item.name} × ${item.quantity}</p>
                            <strong>
                                <fmt:formatNumber value="${item.price * item.quantity}" type="number"/> VNĐ
                            </strong>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <%--            <div class="summary-line">--%>
            <%--                <span>Tạm tính</span>--%>
            <%--                <strong>1.350.000đ</strong>--%>
            <%--            </div>--%>

            <%--            <div class="voucher-box">--%>
            <%--    <label for="voucher">Mã giảm giá</label>--%>
            <%--    <div class="voucher-input">--%>
            <%--        <input type="text" id="voucher" placeholder="Nhập mã giảm giá...">--%>
            <%--        <button type="button" id="applyVoucher">Áp dụng</button>--%>
            <%--    </div>--%>

            <%--                <p id="voucherMessage" class="voucher-msg"></p>--%>
            <%--            </div>--%>

            <%--            <div class="summary-line">--%>
            <%--                <span>Giảm giá</span>--%>
            <%--                <strong id="discountAmount">0đ</strong>--%>
            <%--            </div>--%>

            <div class="summary-total">
                <span>Tổng tiền</span>
                <strong>
                    <fmt:formatNumber value="${totalPrice}" type="number" groupingUsed="true"/> VNĐ
                </strong>
            </div>
        </div>
    </div>
</section>

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
