const tabs = document.querySelectorAll(".tab");
const contents = document.querySelectorAll(".tab-content");

tabs.forEach(tab => {
    tab.addEventListener("click", () => {

        tabs.forEach(t => t.classList.remove("active"));
        contents.forEach(c => c.classList.remove("active"));

        tab.classList.add("active");
        document.getElementById(tab.dataset.tab).classList.add("active");
    });
});


const backToTopBtn = document.getElementById("backToTop");

window.addEventListener("scroll", () => {
    if (window.scrollY > 300) {
        backToTopBtn.style.display = "flex";
    } else {
        backToTopBtn.style.display = "none";
    }
});

backToTopBtn.addEventListener("click", () => {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
});

const stars = document.querySelectorAll(".rating-input .stars i");
const ratingText = document.querySelector(".rating-text");
const ratingValue = document.getElementById("ratingValue");

let selectedRating = 0;

stars.forEach(star => {
    const value = parseInt(star.dataset.value);

    star.addEventListener("mouseenter", () => {
        highlightStars(value);
        ratingText.textContent = value + " sao";
    });

    star.addEventListener("click", () => {
        selectedRating = value;
        ratingValue.value = value;
        ratingText.textContent = value + " sao";
    });

    star.addEventListener("mouseleave", () => {
        highlightStars(selectedRating);
        ratingText.textContent = selectedRating
            ? selectedRating + " sao"
            : "0 sao";
    });
});

function highlightStars(count) {
    stars.forEach(star => {
        star.classList.toggle(
            "active",
            parseInt(star.dataset.value) <= count
        );
    });
}

document.addEventListener("DOMContentLoaded", () => {
    const btn = document.querySelector(".js-add-to-cart");

    if (!btn) return;

    btn.addEventListener("click", () => {
        const productId = btn.dataset.productId;

        fetch(contextPath() + "/Cart", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                action: "add",
                productId: productId
            })
        })
            .then(res => {
                if (res.status === 401) {
                    window.location.href = contextPath() + "/Login";
                    return;
                }

                if (!res.ok) throw new Error();

                showToast("Đã thêm vào giỏ hàng");
            })
            .catch(() => {
                showToast("Có lỗi xảy ra", true);
            });
    });
});

/* ===== Helper ===== */

function showToast(message, isError = false) {
    const toast = document.createElement("div");
    toast.className = "toast";
    toast.textContent = message;

    if (isError) toast.classList.add("error");

    document.body.appendChild(toast);

    setTimeout(() => toast.classList.add("show"), 10);

    setTimeout(() => {
        toast.classList.remove("show");
        setTimeout(() => toast.remove(), 300);
    }, 2000);
}

function contextPath() {
    return document.body.getAttribute("data-context") || "";
}
