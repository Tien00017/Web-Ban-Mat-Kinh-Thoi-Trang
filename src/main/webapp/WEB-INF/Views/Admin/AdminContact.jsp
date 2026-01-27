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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminContact.css">
</head>
<body>
<div class="app">

    <!-- SIDEBAR -->
    <aside class="admin-sidebar">
        <div class="brand">
            <img src="${pageContext.request.contextPath}/Images/Logo.jpg" class="logo-img" alt="Logo">
            <h2>Mắt kính Nông Lâm</h2>
        </div>

        <nav class="admin-nav">
            <a href="${pageContext.request.contextPath}/Admin">Dashboard</a>
            <a href="${pageContext.request.contextPath}/AdminCategory">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/AdminProduct">Quản lí sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminAddProduct">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount">Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/AdminAccount" >Quản lí sự kiện</a>
            <a href="${pageContext.request.contextPath}/AdminContact" class="active">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>

    <main class="layout">

        <aside class="review-pane" aria-labelledby="reviewTitle">
            <div class="pane-head">
                <h2 id="reviewTitle">Danh sách tin nhắn</h2>
                <input id="searchBox" class="search" placeholder="Tìm theo tên/điện thoại"/>
            </div>

            <div id="reviewList" class="review-list">
                <c:forEach var="u" items="${users}">
                    <a class="review-item ${u.id == currentUserId ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/AdminContact?userId=${u.id}"
                       style="text-decoration:none; color:inherit;">

                        <img class="r-avt" src="${pageContext.request.contextPath}/Images/Contact/avatar.jpg" alt="User"/>

                        <div class="r-text">
                            <div class="r-name">
                                <c:choose>
                                    <c:when test="${not empty u.displayName}">${u.displayName}</c:when>
                                    <c:otherwise>${u.fullName}</c:otherwise>
                                </c:choose>
                            </div>

                            <div class="r-sub">
                                <c:choose>
                                    <c:when test="${not empty u.phone}">${u.phone}</c:when>
                                    <c:otherwise>(Chưa có SĐT)</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </a>
                </c:forEach>


                <c:if test="${empty users}">
                    <div style="padding:12px; color:#6b7280;">Chưa có tin nhắn nào.</div>
                </c:if>
            </div>

        </aside>


        <section class="chat-pane" aria-labelledby="chatTitle">

            <header class="chat-head">
                <div class="user-avatar"
                     style="background-image:url('${pageContext.request.contextPath}/Images/Contact/avatar.jpg');"></div>

                <div class="user-meta">
                    <h2 id="chatTitle">
                        <c:choose>
                            <c:when test="${currentUser != null}">
                                ${currentUser.displayName}
                            </c:when>
                            <c:otherwise>Chọn một hội thoại</c:otherwise>
                        </c:choose>
                    </h2>
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

                <c:if test="${empty messages}">
                    <div style="padding:12px; color:#6b7280;">Chưa có tin nhắn.</div>
                </c:if>
            </div>

            <form id="chatForm" class="chat-input" autocomplete="off">
                <input id="chatText" type="text" placeholder="Nhập tin nhắn..."/>
                <button class="btn" type="submit" title="Gửi">
                    <i class="fa-solid fa-paper-plane"></i>
                </button>
            </form>

        </section>
    </main>
</div>
<script>
    const chatForm = document.getElementById("chatForm");
    const chatText = document.getElementById("chatText");

    function getCurrentUserId() {
        return ("${currentUserId}" || "").trim();
    }

    function loadChatBody() {
        const uid = getCurrentUserId();
        if (!uid || uid === "0") {
            document.getElementById("chatBody").innerHTML = "";
            return;
        }

        fetch("AdminMessages?userId=" + encodeURIComponent(uid))
            .then(res => res.ok ? res.text() : "")
            .then(html => {
                document.getElementById("chatBody").innerHTML = html;
            });
    }

    chatForm.addEventListener("submit", function (e) {
        e.preventDefault();
        const uid = getCurrentUserId();
        if (!uid || uid === "0") {
            alert("Vui lòng chọn một người dùng để trả lời.");
            return;
        }

        const msg = (chatText.value || "").trim();
        if (!msg) return;

        fetch("AdminSendMessage", {
            method: "POST",
            headers: {"Content-Type": "application/x-www-form-urlencoded"},
            body: "userId=" + encodeURIComponent(uid) + "&content=" + encodeURIComponent(msg)
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
