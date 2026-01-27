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
    <title>Mắt kính Nông Lâm - Trang chủ</title>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfHomePage.css">
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

<!-- MAIN -->
<main class="container">

    <!-- NEWS / BẢNG TIN -->
    <section class="news-section">
        <h2>Tin tức & Sự kiện</h2>

        <div class="news-slider">
            <button class="slide-btn prev">&#10094;</button>

            <div class="news-track">
                <article class="news-card">
                    <a href="News&Event.html">
                        <img src="${pageContext.request.contextPath}/Images/News&Event/Banner1.jpg">
                    </a>
                </article>

                <article class="news-card">
                    <a href="News&Event.html">
                        <img src="${pageContext.request.contextPath}/Images/News&Event/Banner2.jpg">

                    </a>
                </article>

                <article class="news-card">
                    <a href="News&Event.html">
                        <img src="${pageContext.request.contextPath}/Images/News&Event/Banner3.jpg">
                    </a>
                </article>
            </div>

            <button class="slide-btn next">&#10095;</button>

            <!-- DẤU CHẤM -->
            <div class="slider-dots"></div>
        </div>
    </section>


    <!-- FEATURED PRODUCTS BY CATEGORY -->
    <section class="featured">
        <h2>Sản phẩm nổi bật</h2>

        <div class="feature-category">
            <h3>Kính mát</h3>
            <div class="product-grid">
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/KinhMat1.jpg">
                        <h4>Kính mát A</h4>
                        <p class="price">499,000 VNĐ</p>
                    </a>
                </div>

                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/KinhMat2.jpg">
                        <h4>Kính mát B</h4>
                        <p class="price">699,000 VNĐ</p>
                    </a>
                </div>

                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/KinhMat3.jpg">
                        <h4>Kính mát C</h4>
                        <p class="price">899,000 VNĐ</p>
                    </a>
                </div>
            </div>
        </div>

        <div class="feature-category">
            <h3>Kính cận</h3>
            <div class="product-grid">
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/KinhCan1.jpg">
                        <h4>Kính cận A</h4>
                        <p class="price">299,000 VNĐ</p>
                    </a>
                </div>
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/KinhCan2.png">
                        <h4>Kính cận B</h4>
                        <p class="price">399,000 VNĐ</p>
                    </a>
                </div>
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/KinhCan3.png">
                        <h4>Kính cận C</h4>
                        <p class="price">549,000 VNĐ</p>
                    </a>
                </div>
            </div>
        </div>

        <div class="feature-category">
            <h3>Kính áp tròng</h3>
            <div class="product-grid">
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/KinhApTrong1.jpg">
                        <h4>Áp tròng A</h4>
                        <p class="price">199,000 VNĐ</p>
                    </a>
                </div>
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/KinhApTrong2.jpg">
                        <h4>Áp tròng B</h4>
                        <p class="price">249,000 VNĐ</p>
                    </a>
                </div>
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/KinhApTrong3.jpg">
                        <h4>Áp tròng C</h4>
                        <p class="price">299,000 VNĐ</p>
                    </a>
                </div>
            </div>
        </div>

        <div class="feature-category">
            <h3>Gọng kính</h3>
            <div class="product-grid">
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/GongKinh1.png">
                        <h4>Gọng A</h4>
                        <p class="price">159,000 VNĐ</p>
                    </a>
                </div>
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/GongKinh2.jpg">
                        <h4>Gọng B</h4>
                        <p class="price">219,000 VNĐ</p>
                    </a>
                </div>
                <div class="product-card">
                    <a href="ProductDetail.html" class="product-card">
                        <img src="${pageContext.request.contextPath}/Images/HomePage/GongKinh3.png">
                        <h4>Gọng C</h4>
                        <p class="price">279,000 VNĐ</p>
                    </a>
                </div>
            </div>
        </div>

    </section>

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
<button id="backToTop" aria-label="Lên đầu trang"><i class="fa-solid fa-up-long"></i></button>
<script src="${pageContext.request.contextPath}/JavaScript/HomePage.js"></script>
</body>
</html>
