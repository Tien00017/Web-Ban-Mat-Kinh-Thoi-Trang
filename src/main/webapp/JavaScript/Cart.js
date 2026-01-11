// document.addEventListener("click", function (e) {
//     if (e.target.classList.contains("js-delete")) {
//         e.preventDefault();
//         const item = e.target.closest(".cart-item");
//         item.remove();
//     }
// });


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

document.addEventListener("DOMContentLoaded", () => {
    const checkboxes = document.querySelectorAll(".row-check");

    function recalcTotal() {
        let totalQty = 0;
        let totalPrice = 0;

        checkboxes.forEach(cb => {
            if (cb.checked) {
                const price = Number(cb.dataset.price);
                const qty = Number(cb.dataset.qty);

                totalQty += qty;
                totalPrice += price * qty;
            }
        });

        document.getElementById("totalQty").innerText = totalQty;
        document.getElementById("totalPrice").innerText =
            totalPrice.toLocaleString("vi-VN") + " VNĐ";
    }

    checkboxes.forEach(cb => {
        cb.addEventListener("change", recalcTotal);
    });

    recalcTotal(); // load lần đầu
});