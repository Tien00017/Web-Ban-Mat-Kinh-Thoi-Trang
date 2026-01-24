<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Kết quả tìm kiếm</title>

    <!-- CSS dùng chung với trang chủ -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/CSS/StyleOfHomePage.css">

    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<!-- ================= HEADER (GIỐNG HOME) ================= -->
<jsp:include page="/Views/Header.jsp"/>

<!-- ================= MAIN ================= -->
<main class="container">

    <!-- TIÊU ĐỀ -->
    <section class="news-section">
        <h2>
            Kết quả tìm kiếm cho:
            <span style="color:#1e88e5;">"${keyword}"</span>
        </h2>
    </section>

    <!-- ================= SẢN PHẨM ================= -->
    <section class="featured">
        <h2>Sản phẩm</h2>

        <c:if test="${empty products}">
            <p>❌ Không tìm thấy sản phẩm phù hợp.</p>
        </c:if>

        <div class="product-grid">
            <c:forEach items="${products}" var="p">
                <div class="product-card">
                    <a href="${pageContext.request.contextPath}/ProductDetail?id=${p.id}">
                        <img src="${pageContext.request.contextPath}/${p.thumbnail}">
                        <h4>${p.name}</h4>
                        <p class="price">
                            <fmt:formatNumber value="${p.price}" type="number"/> VNĐ
                        </p>
                    </a>
                </div>
            </c:forEach>
        </div>
    </section>

    <!-- ================= TIN TỨC / SỰ KIỆN ================= -->
    <section class="news-section">
        <h2>Tin tức & Sự kiện</h2>

        <c:if test="${empty newsList}">
            <p>❌ Không tìm thấy tin tức phù hợp.</p>
        </c:if>

        <div class="news-track">
            <c:forEach items="${newsList}" var="n">
                <article class="news-card">
                    <a href="${pageContext.request.contextPath}/NewsDetail?id=${n.id}">
                        <img src="${pageContext.request.contextPath}/${n.image}">
                        <h4 style="padding:10px">${n.title}</h4>
                    </a>
                </article>
            </c:forEach>
        </div>
    </section>

</main>

<!-- ================= FOOTER (GIỐNG HOME) ================= -->
<jsp:include page="/Views/Footer.jsp"/>

</body>
</html>
