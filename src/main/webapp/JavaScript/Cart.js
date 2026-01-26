document.addEventListener("DOMContentLoaded", () => {
    const btn = document.querySelector(".js-checkout");
    if (!btn) return;

    btn.addEventListener("click", () => {
        fetch(CONTEXT_PATH + "/Cart", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                action: "checkout"
            })
        })
            .then(res => res.json())
            .then(data => {
                if (!data.ok) {
                    showToast(data.message, true); // ❌ hết hàng
                    return;
                }

                // ✔ hợp lệ → sang trang Checkout
                window.location.href = CONTEXT_PATH + "/Checkout";
            })
            .catch(() => {
                showToast("Có lỗi xảy ra", true);
            });
    });
});
function showToast(message, isError = false) {
    const toast = document.createElement("div");
    toast.className = "toast-message";
    toast.textContent = message;

    if (isError) {
        toast.style.background = "#d93025";
    } else {
        toast.style.background = "#2e7d32";
    }

    Object.assign(toast.style, {
        position: "fixed",
        bottom: "30px",
        right: "30px",
        color: "#fff",
        padding: "12px 18px",
        borderRadius: "6px",
        zIndex: 9999,
        opacity: 0,
        transition: "opacity .3s ease, transform .3s ease",
        transform: "translateY(10px)"
    });

    document.body.appendChild(toast);

    requestAnimationFrame(() => {
        toast.style.opacity = 1;
        toast.style.transform = "translateY(0)";
    });

    setTimeout(() => {
        toast.style.opacity = 0;
        toast.style.transform = "translateY(10px)";
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}
