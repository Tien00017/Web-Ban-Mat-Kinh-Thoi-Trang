<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Mắt kính Nông Lâm - Đăng nhập</title>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfLogin&Register.css">
</head>
<body>
<div class="form-container">
    <a class="logo" href="#">
        <img src="${pageContext.request.contextPath}/Images/Logo.jpg"
             alt="Logo" class="logo-img">
    </a>
    <a href="${pageContext.request.contextPath}/Home" class="back-home-btn">← Trang chủ</a>

    <h2>Đăng nhập</h2>
    <p>Chào mừng bạn quay lại!</p>
    <form action="${pageContext.request.contextPath}/Login" method="post">
        <input type="email" name="email" placeholder="Email" required>
        <input type="password" name="password" placeholder="Mật khẩu" required>
        <button type="submit">Đăng nhập</button>

        <% if (request.getAttribute("msg") != null) { %>
        <div style="color:green">
            <%= request.getAttribute("msg") %>
        </div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
        <div style="color:red">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>
    </form>
    <div class="divider">hoặc</div>

    <a class="google-btn"
       href="${pageContext.request.contextPath}/GoogleLogin">
        <img src="${pageContext.request.contextPath}/Images/Google/logo.png"
             style="width:18px; vertical-align:middle;">
        Đăng nhập bằng Google
    </a>

    <div class="switch-link">
        <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/Register">Đăng ký</a></p>
        <p>Quên mật khẩu? <a href="${pageContext.request.contextPath}/ForgotPass">Tìm lại mật khẩu</a></p>
    </div>
</div>
<script src="${pageContext.request.contextPath}/JavaScript/ValidateForm.js"></script>
</body>
</html>
