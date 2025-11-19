document.addEventListener("DOMContentLoaded", () => {
    const cartItems = [];

    const recoProducts = [
        {id: "KinhCan", name: "Kính Cận", color: "Đen", price: 2789000, img: "../Images/Cart/KinhCan.jpg"},
        {id: "KinhLao", name: "kính lão", color: "Đen", price: 567000, img: "../Images/Cart/KinhLao.jpg"},
        {id: "KinhRam", name: "Kính Râm", color: "Vàng", price: 277000, img: "../Images/Cart/KinhRam.jpg"}
    ];

    const $ = (s, c = document) => c.querySelector(s);
    const $$ = (s, c = document) => [...c.querySelectorAll(s)];
    const money = n => n.toLocaleString('vi-VN', {style: 'currency', currency: 'VND'});

    function renderCart() {
        const list = $("#cartList");
        const tmpl = $("#cartItemTmpl");
        list.innerHTML = "";

        $("#itemCount").textContent = String(cartItems.length);

        if (cartItems.length === 0) {
            list.innerHTML = `<div class="empty">Your cart is empty.</div>`;
            updateSummary();
            $("#selectAll").checked = false;
            return;
        }

        cartItems.forEach(it => {
            const row = tmpl.content.firstElementChild.cloneNode(true);
            row.dataset.id = it.id;
            row.querySelector("img").src = it.img;
            row.querySelector(".js-name").textContent = it.name;
            row.querySelector(".js-color").textContent = it.color;
            row.querySelector(".js-price").textContent = money(it.price);
            row.querySelector(".js-qty").value = it.qty;
            row.querySelector(".js-subtotal").textContent = money(it.qty * it.price);

            row.querySelector(".row-check").addEventListener("change", updateSummary);
            // tăng/giảm/xoá
            row.querySelector(".js-inc").addEventListener("click", () => changeQty(it.id, +1));
            row.querySelector(".js-dec").addEventListener("click", () => changeQty(it.id, -1));
            row.querySelector(".js-qty").addEventListener("input", e => setQty(it.id, e.target.value));
            row.querySelector(".js-delete").addEventListener("click", (e) => {
                e.preventDefault();
                removeItem(it.id);
            });

            list.appendChild(row);
        });
        $$("#cartList .row-check").forEach(cb => cb.checked = true);
        $("#selectAll").checked = true;

        updateSummary();
    }

    function updateSummary() {
        const selectedRows = $$("#cartList .cart-item").filter(r => r.querySelector(".row-check").checked);
        const selectedIds = selectedRows.map(r => r.dataset.id);
        const selected = cartItems.filter(i => selectedIds.includes(i.id));

        const itemCount = selected.reduce((sum, i) => sum + i.qty, 0);
        const subtotal = selected.reduce((sum, i) => sum + i.qty * i.price, 0);

        $("#summaryItemCount").textContent = String(itemCount);
        $("#summarySubtotal").textContent = money(subtotal);
        $("#summaryTotal").textContent = money(subtotal);
    }

    function changeQty(id, delta) {
        const it = cartItems.find(i => i.id === id);
        if (!it) return;
        it.qty = Math.max(1, it.qty + delta);
        const row = $(`.cart-item[data-id="${id}"]`);
        row.querySelector(".js-qty").value = it.qty;
        row.querySelector(".js-subtotal").textContent = money(it.qty * it.price);
        updateSummary();
    }

    function setQty(id, v) {
        const it = cartItems.find(i => i.id === id);
        if (!it) return;
        const n = Math.max(1, parseInt(v || "1", 10));
        it.qty = n;
        const row = $(`.cart-item[data-id="${id}"]`);
        row.querySelector(".js-subtotal").textContent = money(it.qty * it.price);
        updateSummary();
    }

    function removeItem(id) {
        const idx = cartItems.findIndex(i => i.id === id);
        if (idx === -1) return;
        cartItems.splice(idx, 1);
        renderCart();
    }

    $("#selectAll").addEventListener("change", (e) => {
        $$("#cartList .row-check").forEach(cb => cb.checked = e.target.checked);
        updateSummary();
    });

    $("#massDelete").addEventListener("click", () => {
        const ids = $$("#cartList .cart-item")
            .filter(r => r.querySelector(".row-check").checked)
            .map(r => r.dataset.id);
        ids.forEach(removeItem);
    });

    function renderReco() {
        const wrap = $("#recoList");
        const tmpl = $("#recoCardTmpl");
        wrap.innerHTML = "";

        recoProducts.forEach(p => {
            const card = tmpl.content.firstElementChild.cloneNode(true);
            card.querySelector(".reco-img").src = p.img;
            card.querySelector(".js-name").textContent = p.name;
            card.querySelector(".js-price").textContent = money(p.price);
            card.querySelector(".js-sub").textContent = `${p.name} ${p.color} `;

            card.querySelector(".js-add").addEventListener("click", () => {
                const exist = cartItems.find(i => i.id === p.id);
                if (exist) exist.qty += 1;
                else cartItems.push({...p, qty: 1});
                renderCart();
                document.querySelector(".cart")?.scrollIntoView({behavior: "smooth", block: "start"});
            });

            wrap.appendChild(card);
        });
    }

    renderCart();
    renderReco();
});
