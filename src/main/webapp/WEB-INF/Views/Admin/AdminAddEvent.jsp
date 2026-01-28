<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <meta charset="UTF-8">
    <title>Admin - Thêm Sự Kiện</title>
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
        <h2>Thêm sự kiện</h2>

        <div class="event-layout">

            <!-- LEFT: FORM CARD -->
            <div class="event-left">
                <form id="eventCreateForm"
                      action="${pageContext.request.contextPath}/admin/event/create"
                      method="post"
                      enctype="multipart/form-data"
                      class="event-form">

                    <label>Tên sự kiện</label>
                    <input type="text" name="title" required>

                    <label>Nội dung</label>
                    <textarea name="content"></textarea>

                    <label>Ngày bắt đầu</label>
                    <input type="date" name="startDate" required>

                    <label>Ngày kết thúc</label>
                    <input type="date" name="endDate" required>

                    <label>Loại giảm giá</label>
                    <select name="discountType">
                        <option value="NONE">Không giảm</option>
                        <option value="PERCENT">Giảm %</option>
                        <option value="AMOUNT">Giảm tiền</option>
                    </select>

                    <label>Banner chính (URL)</label>
                    <input type="url" name="mainBannerUrl" id="mainBannerUrl" placeholder="https://...">

                    <label>Hoặc upload banner chính</label>
                    <input type="file" name="mainBannerFile" id="mainBannerFile" accept="image/*">

                    <img id="mainBannerPreview" style="display:none; max-width:100%; border-radius:12px; margin-top:10px; border:1px solid #e6e8f2;" alt="Preview">


                    <label>Banner phụ (URL)</label>
                    <div id="extraBanners"></div>
                    <button type="button" class="btn ghost" id="addBannerBtn">+ Thêm banner phụ</button>
                    <button type="submit" class="btn">Tạo sự kiện</button>
                </form>
            </div>

            <!-- RIGHT: PRODUCT PANEL CARD -->
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
                                <!-- form="eventCreateForm" để checkbox submit theo form bên trái -->
                                <input type="checkbox"
                                       name="productIds"
                                       value="${p.id}"
                                       form="eventCreateForm">
                                <span>${p.productName}</span>
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </aside>

        </div>
    </div>
</div>

<script>
    (function () {
        // ===== Product search =====
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

        list.addEventListener('change', function (e) {
            if (e.target && e.target.matches('input[type="checkbox"]')) updateCount();
        });

        search.addEventListener('input', filter);
        updateCount();

        // ===== Banner preview (URL + File) =====
        const urlInput = document.getElementById("mainBannerUrl");
        const fileInput = document.getElementById("mainBannerFile");
        const previewImg = document.getElementById("mainBannerPreview");

        function showPreview(src) {
            if (!src) {
                previewImg.style.display = "none";
                previewImg.src = "";
                return;
            }
            previewImg.src = src;
            previewImg.style.display = "block";
        }

        urlInput.addEventListener("input", () => {
            const url = (urlInput.value || "").trim();
            // nếu đang chọn file thì cứ ưu tiên URL hay file tùy bạn
            showPreview(url);
        });

        fileInput.addEventListener("change", () => {
            const f = fileInput.files && fileInput.files[0];
            if (!f) return;
            const objUrl = URL.createObjectURL(f);
            showPreview(objUrl);
            // (tuỳ chọn) nếu chọn file thì xoá URL để ưu tiên file rõ ràng:
            // urlInput.value = "";
        });

        // ===== Extra banners =====
        const extraWrap = document.getElementById("extraBanners");
        const addBtn = document.getElementById("addBannerBtn");
        const formId = "eventCreateForm";

        addBtn.addEventListener("click", () => {
            const div = document.createElement("div");
            div.style.display = "flex";
            div.style.gap = "10px";
            div.style.marginTop = "10px";

            div.innerHTML = `
        <input type="url" name="bannerUrls" placeholder="https://..." style="flex:1;" form="${formId}">
        <button type="button" class="btn ghost">X</button>
      `;

            div.querySelector("button").addEventListener("click", () => div.remove());
            extraWrap.appendChild(div);
        });

    })();
</script>


</body>
</html>
