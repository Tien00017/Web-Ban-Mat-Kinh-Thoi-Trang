<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="utf-8">
    <title>Admin - Quản lý danh mục</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminCategory.css">
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
            <a href="${pageContext.request.contextPath}/AdminCategory" class="active">Quản lí danh mục</a>
            <a href="${pageContext.request.contextPath}/AdminProduct">Quản lí sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminAddProduct">Thêm sản phẩm</a>
            <a href="${pageContext.request.contextPath}/AdminOrders">Quản lí đơn hàng</a>
            <a href="${pageContext.request.contextPath}/AdminAccount">Quản lí tài khoản</a>
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
            <h1>Quản lý danh mục</h1>
        </div>

        <div class="content">

            <div class="left">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>TÊN DANH MỤC</th>
                        <th></th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="cat" items="${categories}">
                        <tr>
                            <td>${cat.id}</td>
                            <td>${cat.categoryName}</td>
                            <td class="actions">

                                <form action="${pageContext.request.contextPath}/AdminCategory" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${cat.id}">
                                    <input type="text" name="name" value="${cat.categoryName}" style="width:160px;">
                                    <button class="btn ghost" type="submit">Sửa</button>
                                </form>

                                <form action="${pageContext.request.contextPath}/AdminCategory" method="post" style="display:inline;"
                                      onsubmit="return confirm('Xóa danh mục này?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${cat.id}">
                                    <button class="btn ghost" type="submit">Xóa</button>
                                </form>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>



                </table>
            </div>

            <aside class="right">
                <h3>Thêm danh mục</h3>

                <form action="${pageContext.request.contextPath}/AdminCategory" method="post">
                    <input type="hidden" name="action" value="create">

                    <div class="form-row">
                        <input type="text" name="name" placeholder="Tên Danh Mục" required>
                    </div>

                    <div class="form-actions">
                        <button class="btn" type="submit">Lưu</button>
                    </div>
                </form>

                <c:if test="${param.err == 'empty'}">
                    <p style="color:red;">Tên danh mục không được để trống.</p>
                </c:if>
                <c:if test="${param.err == 'exists'}">
                    <p style="color:red;">Tên danh mục đã tồn tại.</p>
                </c:if>
                <c:if test="${param.err == 'has_products'}">
                    <p style="color:red;">Danh mục đang có sản phẩm, không thể xóa.</p>
                </c:if>
                <c:if test="${param.ok != null}">
                    <p style="color:green;">Thao tác thành công!</p>
                </c:if>
            </aside>


        </div>

    </section>


</div>
</body>
</html>
