<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Admin - Thêm / Sửa sản phẩm</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfAdminAddProduct.css">
</head>
<body>

<div class="app">

    <!-- SIDEBAR -->
    <aside class="admin-sidebar">
        <h2>Mắt kính Nông Lâm</h2>
        <nav>
            <a href="${pageContext.request.contextPath}/AdminProduct">
                Quản lý sản phẩm
            </a>
            <a class="active" href="${pageContext.request.contextPath}/AdminAddProduct">
                Thêm / Sửa sản phẩm
            </a>
        </nav>
    </aside>

    <!-- MAIN -->
    <section class="admin-main">
        <h1>
            ${mode == 'edit' ? 'Chỉnh sửa sản phẩm' : 'Thêm sản phẩm mới'}
        </h1>

        <form method="post"
              action="${pageContext.request.contextPath}/AdminAddProduct"
              enctype="multipart/form-data">

            <!-- EDIT MODE -->
            <c:if test="${mode == 'edit'}">
                <input type="hidden" name="id" value="${product.id}">
            </c:if>

            <!-- BASIC INFO -->
            <input name="productName"
                   placeholder="Tên sản phẩm"
                   value="${product.productName}"
                   required>

            <input name="brand"
                   placeholder="Thương hiệu"
                   value="${product.brand}">

            <input type="number"
                   name="price"
                   placeholder="Giá (VNĐ)"
                   value="${product.price}"
                   required>

            <input type="number"
                   name="stock"
                   placeholder="Số lượng kho"
                   value="${product.stock}"
                   required>

            <!-- CATEGORY -->
            <label>Loại kính</label>
            <select name="categoryId" required>
                <option value="">-- Chọn loại --</option>
                <option value="1" ${product.categoryId == 1 ? 'selected' : ''}>Kính cận</option>
                <option value="2" ${product.categoryId == 2 ? 'selected' : ''}>Kính mát</option>
                <option value="3" ${product.categoryId == 3 ? 'selected' : ''}>Gọng kính</option>
            </select>

            <!-- COLOR (TUỲ Ý) -->
            <label>Màu sắc</label>
            <div style="display:flex; gap:8px">
                <input type="color"
                       onchange="document.getElementById('colorInput').value=this.value">
                <input type="text"
                       id="colorInput"
                       name="color"
                       placeholder="Nhập tên màu hoặc mã màu"
                       value="${product.color}"
                       required>
            </div>

            <!-- ATTRIBUTE -->
            <input name="material"
                   placeholder="Chất liệu"
                   value="${product.material}">

            <input name="style"
                   placeholder="Kiểu dáng"
                   value="${product.style}">

            <!-- DESCRIPTION -->
            <textarea name="generalDescription"
                      placeholder="Mô tả sản phẩm">${product.generalDescription}</textarea>

            <!-- IMAGE -->
            <label>Hình ảnh sản phẩm</label>
            <input type="file" name="images" multiple>

            <button type="submit">
                ${mode == 'edit' ? 'Cập nhật sản phẩm' : 'Thêm sản phẩm'}
            </button>
        </form>
    </section>

</div>

</body>
</html>
