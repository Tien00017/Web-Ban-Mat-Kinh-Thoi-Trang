<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Sửa Sự Kiện</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleOfAdminEvent.css">
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
            <a href="${pageContext.request.contextPath}/admin/event/list" class="active">Quản lí sự kiện</a>
            <a href="${pageContext.request.contextPath}/AdminContact">Liên hệ</a>
        </nav>

        <div class="side-footer muted">
            Admin • Bạn đang đăng nhập với quyền Quản trị
        </div>
    </aside>

    <!-- CONTENT -->
    <div class="container">
        <h2>Sửa sự kiện</h2>

        <div class="event-layout">

            <!-- LEFT: FORM -->
            <div class="event-left">
                <form action="${pageContext.request.contextPath}/admin/event/update" method="post"
                      enctype="multipart/form-data" class="event-form">
                    <input type="hidden" name="id" value="${event.id}">

                    <label>Tên sự kiện</label>
                    <input type="text" name="title" value="${event.title}" required>

                    <label>Nội dung</label>
                    <textarea name="content">${event.content}</textarea>

                    <label>Ngày bắt đầu</label>
                    <input type="date" name="startDate" value="${event.startDate}" required>

                    <label>Ngày kết thúc</label>
                    <input type="date" name="endDate" value="${event.endDate}" required>

                    <label>Loại giảm giá</label>
                    <select name="discountType">
                        <option value="NONE" ${event.discountType=='NONE'?'selected':''}>Không giảm</option>
                        <option value="PERCENT" ${event.discountType=='PERCENT'?'selected':''}>Giảm %</option>
                        <option value="AMOUNT" ${event.discountType=='AMOUNT'?'selected':''}>Giảm tiền</option>
                    </select>

                    <label>Giá trị giảm</label>
                    <input type="number" name="discountValue" value="${event.discountValue}" step="0.01">

                    <label>Trạng thái</label>
                    <select name="status">
                        <option value="ACTIVE" ${event.status=='ACTIVE'?'selected':''}>Hoạt động</option>
                        <option value="INACTIVE" ${event.status=='INACTIVE'?'selected':''}>Tạm dừng</option>
                        <option value="EXPIRED" ${event.status=='EXPIRED'?'selected':''}>Hết hạn</option>
                    </select>
                    <label>Banner chính (URL)</label>
                    <label>Banner chính (URL)</label>
                    <input type="url" name="mainBannerUrl" id="mainBannerUrl" placeholder="https://...">

                    <label>Hoặc upload banner chính</label>
                    <input type="file" name="mainBannerFile" id="mainBannerFile" accept="image/*">

                    <img id="mainBannerPreview"
                         style="display:none; max-width:100%; border-radius:12px; margin-top:10px; border:1px solid #e6e8f2;"
                         alt="Preview">


                    <label>Banner phụ (URL)</label>
                    <div id="extraBanners">
                        <c:forEach var="u" items="${extraBannerUrls}">
                            <div style="display:flex; gap:10px; margin-top:10px;">
                                <input type="url" name="bannerUrls" value="${u}" style="flex:1;">
                                <button type="button" class="btn ghost" onclick="this.parentElement.remove()">X</button>
                            </div>
                        </c:forEach>
                    </div>

                    <button type="button" class="btn ghost" id="addBannerBtn">+ Thêm banner phụ</button>
                    <button type="submit" class="btn">Cập nhật</button>
                </form>
            </div>

            <!-- RIGHT: PRODUCTS -->
            <aside class="event-right">
                <div class="product-panel">
                    <div class="product-panel__header">
                        <h3>Sản phẩm áp dụng</h3>

                        <input
                                id="productSearch"
                                type="text"
                                class="search-input"
                                placeholder="Tìm sản phẩm theo tên..."
                                autocomplete="off"
                        />

                        <div class="muted small" id="productCount"></div>
                    </div>

                    <div class="product-list" id="productList">
                        <c:forEach var="p" items="${products}">
                            <label class="product-item" data-name="${p.productName}">
                                <input type="checkbox"
                                       name="productIds"
                                       value="${p.id}"
                                       <c:if test="${selectedProductIds.contains(p.id)}">checked</c:if>>
                                <span>${p.productName}</span>
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </aside>

        </div>
    </div>

    <script>
        (function () {
            const search = document.getElementById('productSearch');
            const list = document.getElementById('productList');
            const items = Array.from(list.querySelectorAll('.product-item'));
            const count = document.getElementById('productCount');

            function updateCount() {
                const visible = items.filter(i => i.style.display !== 'none').length;
                const checked = items.filter(i => i.querySelector('input').checked).length;
                count.textContent = `Hiển thị: ${visible} sản phẩm • Đã chọn: ${checked}`;
            }

            function filter() {
                const q = (search.value || '').trim().toLowerCase();
                items.forEach(item => {
                    const name = (item.getAttribute('data-name') || '').toLowerCase();
                    item.style.display = name.includes(q) ? '' : 'none';
                });
                updateCount();
            }

            // cập nhật số lượng khi tick
            list.addEventListener('change', function (e) {
                if (e.target && e.target.matches('input[type="checkbox"]')) updateCount();
            });

            search.addEventListener('input', filter);
            updateCount();
        })();
        const mainInput = document.getElementById("mainBannerUrl");
        const preview = document.getElementById("mainBannerPreview");
        mainInput.addEventListener("input", () => {
            const url = mainInput.value.trim();
            if (!url) {
                preview.style.display = "none";
                preview.src = "";
                return;
            }
            preview.src = url;
            preview.style.display = "block";
        });

        const extraWrap = document.getElementById("extraBanners");
        document.getElementById("addBannerBtn").addEventListener("click", () => {
            const div = document.createElement("div");
            div.style.display = "flex";
            div.style.gap = "10px";
            div.style.marginTop = "10px";

            div.innerHTML = `
      <input type="url" name="bannerUrls" placeholder="https://..." style="flex:1;">
      <button type="button" class="btn ghost">X</button>
    `;
            div.querySelector("button").addEventListener("click", () => div.remove());
            extraWrap.appendChild(div);
        });
    </script>

</div>

</body>
</html>

