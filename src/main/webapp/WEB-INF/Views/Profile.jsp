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
    <title>Mắt kính Nông Lâm - Trang profile</title>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfProfile.css">
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
                <a href="${pageContext.request.contextPath}/Home">Trang chủ</a>
                <a href="AboutUs.html">Giới thiệu</a>
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
                <a href="Cart.html" aria-label="Giỏ hàng">
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
        <a href="Kinh_Can.html" class="cat">Kính Cận</a>
        <a href="Kinh_Mat.html" class="cat">Kính Mát</a>
        <a href="Kinh_Ap_Trong.html" class="cat">Kính Áp Tròng</a>
        <a href="Gong_Kinh.html" class="cat">Gọng Kính</a>
    </nav>
</header>

<main class="container grid">

    <!-- Tóm tắt hồ sơ -->
    <section class="card profile" aria-labelledby="profile-title">
        <h2 id="profile-title" class="sr-only">Tóm tắt hồ sơ</h2>

        <div class="avatar-wrap">
            <img id="avatarImg" src="${pageContext.request.contextPath}/Images/Profile/ball.png" class="avatar"
                 alt="Ảnh đại diện người dùng"/>
            <input type="file" id="avatarUpload" accept="image/*" style="display:none"/>
            <label for="avatarUpload" class="btn small">Đổi ảnh</label>
        </div>

        <div class="profile-info">
            <p class="name">Tên hiển thị</p>
            <p class="email">email@example.com</p>
            <p class="phone">(+84) 0123 456 789</p>
        </div>
        <div class="history-actions">
            <a href="OrderHistory.html" class="btn primary">Xem lịch sử đơn hàng</a>
        </div>
    </section>


    <!-- Form chỉnh sửa thông tin -->
    <section class="card" aria-labelledby="edit-title">
        <h2 id="edit-title">Chỉnh sửa thông tin</h2>
        <form novalidate>
            <div class="form-grid">

                <div class="field">
                    <label for="fullName">Họ và tên <span class="req">*</span></label>
                    <input id="fullName" type="text" placeholder="Họ và tên" required/>
                    <small class="error"> </small>
                </div>

                <div class="field">
                    <label for="email">Email <span class="req">*</span></label>
                    <input id="email" type="email" placeholder="email@example.com" required/>
                    <small class="error"></small>
                </div>

                <div class="field">
                    <label for="phone">Số điện thoại</label>
                    <input id="phone" type="tel" placeholder="0123 456 789"/>
                    <small class="error"></small>
                </div>

                <div class="field">
                    <label for="birthday">Ngày sinh</label>
                    <input id="birthday" type="date" placeholder="2000-01-01"/>
                </div>

                <div class="field">
                    <label for="gender">Giới tính</label>
                    <select id="gender">
                        <option value="" selected>-- Chọn --</option>
                        <option value="male">Nam</option>
                        <option value="female">Nữ</option>
                        <option value="other">Khác</option>
                    </select>
                </div>

                <div class="field span-2">
                    <label for="address">Địa chỉ</label>
                    <textarea id="address" rows="3" placeholder="123 Đường ABC, Quận 1, TP.HCM"></textarea>
                </div>
            </div>

            <div class="form-actions">
                <button type="button" class="btn primary disabled">Lưu thay đổi</button>
            </div>
        </form>
    </section>


    <!-- Khu vực bảo mật -->
    <section class="card" aria-labelledby="security-title">
        <h2 id="security-title">Bảo mật</h2>
        <form action="${pageContext.request.contextPath}/Profile" method="post">
            <input type="hidden" name="action" value="changePassword"/>

            <div class="field">
                <label for="currentPassword">Mật khẩu hiện tại</label>
                <input id="currentPassword" type="password"/>
            </div>

            <div class="field">
                <label for="newPassword">Mật khẩu mới</label>
                <input id="newPassword" type="password"/>
            </div>

            <div class="field">
                <label for="confirmPassword">Nhập lại mật khẩu</label>
                <input id="confirmPassword" type="password"/>
                <small class="error"></small>
            </div>

            <div class="form-actions">
                <button type="button" id="changePassBtn" class="btn">Đổi mật khẩu</button>
            </div>
        </form>

        <form action="${pageContext.request.contextPath}/Profile" method="post">
            <div class="form-actions">
                <input type="hidden" name="action" value="logout"/>
                <button type="submit" class="btn danger">Đăng xuất</button>
            </div>
        </form>

        </div>
        </form>
    </section>
</main>

<script src="../src/main/webapp/JavaScript/Profile.js"></script>
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
</body>
</html>
