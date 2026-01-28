<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>
        ${mode == 'edit' ? 'Admin - Chỉnh sửa sản phẩm' : 'Admin - Thêm sản phẩm'}
    </title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfAdminAddProduct.css">
</head>

<body>
<div class="app">

    <section class="admin-main">
        <div class="page-title">
            <h1>
                ${mode == 'edit' ? 'Chỉnh sửa sản phẩm' : 'Thêm sản phẩm'}
            </h1>
        </div>

        <div class="content">
            <form class="add-form"
                  method="post"
                  action="${pageContext.request.contextPath}/AdminAddProduct">

                <!-- EDIT MODE -->
                <c:if test="${mode == 'edit'}">
                    <input type="hidden" name="id" value="${product.id}">
                    <input type="hidden" name="soldQuantity" value="${product.soldQuantity}">
                    <input type="hidden" name="deleted" value="${product.deleted}">
                </c:if>

                <!-- CATEGORY -->
                <div class="form-group">
                    <label>Danh mục</label>
                    <select name="categoryId" required>
                        <option value="">Chọn danh mục</option>
                        <option value="1" ${product.categoryId == 1 ? 'selected' : ''}>Kính cận</option>
                        <option value="2" ${product.categoryId == 2 ? 'selected' : ''}>Kính mát</option>
                        <option value="3" ${product.categoryId == 3 ? 'selected' : ''}>Kính áp tròng</option>
                        <option value="4" ${product.categoryId == 4 ? 'selected' : ''}>Gọng kính</option>
                    </select>
                </div>

                <!-- PRODUCT NAME -->
                <div class="form-group">
                    <label>Tên sản phẩm</label>
                    <input type="text" name="productName"
                           value="${product.productName}" required>
                </div>

                <!-- BRAND -->
                <div class="form-group">
                    <label>Thương hiệu</label>
                    <input type="text" name="brand"
                           value="${product.brand}">
                </div>

                <!-- PRICE -->
                <div class="form-group">
                    <label>Giá</label>
                    <input type="number" name="price"
                           value="${product.price}" required>
                </div>

                <!-- STOCK -->
                <div class="form-group">
                    <label>Số lượng kho</label>
                    <input type="number" name="stock"
                           value="${product.stock}" required>
                </div>

                <!-- ORIGIN -->
                <div class="form-group">
                    <label>Xuất xứ</label>
                    <input type="text" name="origin"
                           value="${product.origin}">
                </div>

                <!-- GENERAL DESCRIPTION -->
                <div class="form-group full">
                    <label>Mô tả chung</label>
                    <textarea name="generalDescription" rows="4">
${product.generalDescription}</textarea>
                </div>

                <!-- SHIPPING INFO -->
                <div class="form-group full">
                    <label>Thông tin giao hàng</label>
                    <textarea name="shippingInfo" rows="3">
${product.shippingInfo}</textarea>
                </div>

                <!-- GUARANTEE -->
                <div class="form-group full">
                    <label>Bảo hành</label>
                    <textarea name="guaranteeDetails" rows="3">
${product.guaranteeDetails}</textarea>
                </div>

                <button type="submit" class="btn primary">
                    ${mode == 'edit' ? 'Cập nhật sản phẩm' : 'Thêm sản phẩm'}
                </button>

            </form>
        </div>
    </section>

</div>
</body>
</html>
