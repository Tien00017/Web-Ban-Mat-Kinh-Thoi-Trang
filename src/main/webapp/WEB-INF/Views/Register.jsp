<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Mắt kính Nông Lâm - Đăng ký</title>
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfLogin&Register.css">
</head>
<body>
<div class="form-container">
    <a class="logo" href="#"><img src="${pageContext.request.contextPath}/Images/Logo.jpg" alt="Logo" class="logo-img"></a>
    <a href="${pageContext.request.contextPath}/Home" class="back-home-btn">← Trang chủ</a>
    <h2>Đăng ký</h2>
    <p>Tạo tài khoản mới của bạn!</p>
    <form>
        <input type="text" placeholder="Tên hiển thị" required>
        <input type="email" placeholder="Email" required>
        <input type="password" placeholder="Mật khẩu" required>
        <input type="password" placeholder="Xác nhận mật khẩu" required>
        <button type="submit">Đăng ký</button>
    </form>
    <div class="switch-link">
        Đã có tài khoản? <a href="${pageContext.request.contextPath}/Login">Đăng nhập</a>
    </div>
</div>
</body>
</html>
