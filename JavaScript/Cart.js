document.addEventListener("click", function (e) {
    if (e.target.classList.contains("js-delete")) {
        e.preventDefault();
        const item = e.target.closest(".cart-item");
        item.remove();
    }
});


// Tăng giảm số lượng
document.addEventListener("click", function (e) {
    const btn = e.target;

    if (btn.classList.contains("js-inc")) {
        const input = btn.parentElement.querySelector(".js-qty");
        input.value = Number(input.value) + 1;
    }

    if (btn.classList.contains("js-dec")) {
        const input = btn.parentElement.querySelector(".js-qty");
        let val = Number(input.value);
        if (val > 1) input.value = val - 1;
    }
});