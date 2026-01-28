<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin-Quản lí tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminAccount.css">
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
            <a href="${pageContext.request.contextPath}/Admin" >Dashboard</a>
            <a href="${pageContext.request.contextPath}/AdminCategory">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/AdminProduct">Quản lí sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminAddProduct">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount" class="active">Quản lí tài khoản</a>
            <a href="${pageContext.request.contextPath}/admin/event/list" >Quản lí sự kiện</a>
            <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>


    <!-- MAIN -->
    <section class="main">

        <div class="page-title">
            <h1>Quản lý tài khoản</h1>
        </div>

        <div class="panel">
            <div class="left">
                <table class="accounts-table">
                    <thead>
                    <tr>
                        <th style="width:60px">ID</th>
                        <th style="width:220px">TÊN</th>
                        <th style="width:170px">SỐ ĐIỆN THOẠI</th>
                        <th style="width:220px">EMAIL</th>
                        <th style="width:120px">NGÀY SINH</th>
                        <th style="width:120px">GIỚI TÍNH</th>
                        <th>ĐỊA CHỈ</th>
                        <th>TRẠNG THÁI</th>
                        <th style="width:150px">VAI TRÒ</th>
                        <th style="width:150px"></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="u" items="${users}">
                        <tr>
                            <td>U${u.id}</td>
                            <td>${u.fullName}</td>
                            <td>${u.phone}</td>
                            <td>${u.email}</td>
                            <td>${u.birthDate}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${u.gender == 1}">Nam</c:when>
                                    <c:when test="${u.gender == 0}">Nữ</c:when>
                                    <c:otherwise>Khác</c:otherwise>
                                </c:choose>
                            </td>

                            <td>${u.address}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${u.status}">
                                        <span class="open">Mở</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="close">Khóa</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:choose>
                                    <c:when test="${u.role == 1}">
                                        <span class="tag pending">user</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="tag success">admin</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td class="text-right">
                                <!-- Form update status/role -->
                                <form action="${pageContext.request.contextPath}/AdminAccount" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${u.id}">

                                    <select name="status">
                                        <option value="1" ${u.status ? 'selected' : ''}>Mở</option>
                                        <option value="0" ${!u.status ? 'selected' : ''}>Khóa</option>
                                    </select>

                                    <select name="role">
                                        <option value="0" ${u.role == 0 ? 'selected' : ''}>admin</option>
                                        <option value="1" ${u.role == 1 ? 'selected' : ''}>user</option>
                                    </select>

                                    <button class="btn ghost" type="submit">Lưu</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>
            </div>
        </div>

    </section>


</div>
</body>
</html>