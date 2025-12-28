<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Mắt kính Nông Lâm - Quên mật khẩu</title>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfLogin&Register.css">
</head>
<body>
<div class="form-container">
    <a class="logo" href="#"><img src="${pageContext.request.contextPath}/Images/Logo.jpg" alt="Logo" class="logo-img"></a>
    <a href="${pageContext.request.contextPath}/Home" class="back-home-btn">← Trang chủ</a>

    <h2>Quên mật khẩu</h2>
    <p>Chúng tôi sẽ hỗ trợ bạn tìm lại tài khoản!</p>
    <form action="${pageContext.request.contextPath}/ForgotPass" method="post">

        <input type="email"
               name="email"
               placeholder="Nhập email"
               value="${email}"
            <%= request.getAttribute("otpSent") != null ? "readonly" : "" %>
               required>

        <% if (request.getAttribute("otpSent") != null) { %>
        <input type="text" name="otp" placeholder="Nhập OTP" required>
        <% } %>

        <% if (request.getAttribute("otpSent") == null) { %>
        <button type="submit" name="action" value="sendOTP">Gửi OTP</button>
        <% } else { %>
        <button type="submit" name="action" value="verifyOTP">Xác nhận OTP</button>
        <% } %>

        <% if (request.getAttribute("msg") != null) { %>
        <div style="color:green"><%= request.getAttribute("msg") %></div>
        <% } %>

        <% if (request.getAttribute("error") != null) { %>
        <div style="color:red"><%= request.getAttribute("error") %></div>
        <% } %>

    </form>


    <% if (request.getAttribute("otpVerified") != null) { %>
    <form action="${pageContext.request.contextPath}/ForgotPass"
          method="post"
          onsubmit="return validateForm();">
        <input type="hidden" name="userId" value="${userId}">

        <input type="password"
               name="newPassword"
               placeholder="Mật khẩu mới"
               required>

        <input type="password"
               name="confirm"
               placeholder="Xác nhận mật khẩu"
               required>

        <button type="submit" name="action" value="resetPass">
            Đổi mật khẩu
        </button>

    </form>
    <% } %>


    <div class="switch-link">
        <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/Register">Đăng ký</a></p>
    </div>
</div>
<script src="${pageContext.request.contextPath}/JavaScript/ValidateForm.js"></script>
</body>
</html>
