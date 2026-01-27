<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Mắt kính Nông Lâm - Trang contact</title>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfContact.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
          integrity="sha512-..." crossorigin="anonymous" referrerpolicy="no-referrer"/>

</head>
<body>
<!-- HEADER -->
<header class="site-header">
    <div class="header-inner">
        <div class="header-left">
            <a class="logo" href="#"><img src="${pageContext.request.contextPath}/Images/Logo.jpg" alt="Logo"
                                          class="logo-img">Mắt kính Nông Lâm</a>
            <nav class="main-nav" aria-label="Chính">
                <a href="${pageContext.request.contextPath}/Home">Trang chủ</a>
                <a href="${pageContext.request.contextPath}/AboutUs">Giới thiệu</a>
                <a href="${pageContext.request.contextPath}/Contact" class="active">Liên hệ</a>
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
                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                        <a class="btn-outline" href="${pageContext.request.contextPath}/Login">Đăng nhập</a>
                        <a class="btn-primary" href="${pageContext.request.contextPath}/Register">Đăng ký</a>
                    </c:when>
                </c:choose>
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

<main class="page-main">
    <div class="layout">

        <aside class="review-pane" aria-labelledby="reviewTitle">
            <!-- STORE PROFILE (nổi bật) -->
            <div class="store-card">
                <div class="store-media">
                    <img src="${pageContext.request.contextPath}/Images/Logo.jpg" alt="Logo cửa hàng"
                         class="store-logo">
                </div>
                <div class="store-info">
                    <h2 class="store-name">Mắt kính Nông Lâm</h2>
                    <div class="store-meta">
                        <div class="store-rating" aria-hidden="true">
                        <span class="stars-inline">
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                            <i class="fa-solid fa-star"></i>
                        </span>
                            <span class="rating-val">4.8</span>
                        </div>
                        <div class="store-line"><strong>Địa chỉ:</strong> 123 Điện Biên Phủ, Q.1, TP.HCM</div>
                        <div class="store-line"><Strong>Giờ mở cửa:</Strong> 08:00 — 20:00 (T2–CN)</div>
                        <div class="store-line"><strong>Hotline:</strong><a href="#">(+84) 38 5667 219</a></div>
                        <div class="store-line"><strong>Email:</strong> <a href="#">info@matkinh.com</a></div>
                    </div>

                    <div class="store-actions">
                        <a href="#" class="icon-link" title="Gọi ngay"><i class="fa-solid fa-phone"></i></a>
                        <a href="#" class="icon-link" title="Gửi email"><i class="fa-solid fa-envelope"></i></a>
                        <a href="https://maps.google.com" target="_blank" rel="noopener" class="btn">Chỉ đường</a>
                    </div>
                </div>
            </div>
            <div class="img-map">
                <img src="${pageContext.request.contextPath}/Images/Contact/map.png" alt="google map" class="map">
            </div>

        </aside>


        <section class="chat-pane" aria-labelledby="chatTitle">

            <header class="chat-head">
                <div class="user-avatar"
                     style="background-image:url('${pageContext.request.contextPath}/Images/Contact/avatar.jpg');"></div>

                <div class="user-meta">
                    <h2 id="chatTitle">CỬA HÀNG MẮT KÍNH NÔNG LÂM</h2>
                    <div id="chatSub" class="muted">Chat với chúng tôi</div>
                </div>
            </header>

            <div id="chatBody" class="chat-body">
                <c:forEach var="m" items="${messages}">
                    <div class="row ${m.senderId == sessionScope.user.id ? 'me-row' : ''}">
                        <div class="msg ${m.senderId == sessionScope.user.id ? 'me' : ''}">
                                ${m.content}
                        </div>
                    </div>
                </c:forEach>
            </div>

            <form id="chatForm" class="chat-input" autocomplete="off">
                <input id="chatText" type="text" placeholder="Nhập tin nhắn..."/>
                <button class="btn" type="submit" title="Gửi">
                    <i class="fa-solid fa-paper-plane"></i>
                </button>
            </form>

        </section>
    </div>
</main>

<script>
    const chatForm = document.getElementById("chatForm");
    const chatText = document.getElementById("chatText");

    function loadChatBody() {
        fetch("ContactMessages")
            .then(res => res.ok ? res.text() : "")
            .then(html => {
                const chatBody = document.getElementById("chatBody");
                chatBody.scrollTop = chatBody.scrollHeight;
                document.getElementById("chatBody").innerHTML = html;

            });
    }

    chatForm.addEventListener("submit", function (e) {
        e.preventDefault();
        const msg = (chatText.value || "").trim();
        if (!msg) return;

        fetch("SendMessage", {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded"},
            body: "content=" + encodeURIComponent(msg)
        }).then(() => {
            chatText.value = "";
            loadChatBody();
        });
    });

    loadChatBody();
    setInterval(loadChatBody, 3000);
</script>


</body>
</html>
