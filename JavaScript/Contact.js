const users = [
    {
        id: "u01",
        name: "lê Thị Anh Thư",
        phone: "0987 654 321",
        product: "Kính Cận C07",
        rating: 5,
        content: "Mẫu mã rất chắc chắn, đeo lên xinh.",
        avatar: "../Images/Contact/avatar.jpg",
        photos: ["../Images/Contact/Review.jpg", "../Images/Contact/Review2.jpg"],
        messages: [
            "Shop ơi còn sản phẩm nào tương tự mà màu bạc không?",
            "Ok mình thấy mẫu C09 đẹp đó để mình đặt 1 cái"
        ]
    },
    {
        id: "u02",
        name: "Lee Min Ho",
        phone: "0912 345 678",
        product: "Kính Lão L04",
        rating: 4,
        content: "Kính hơi nặng đầu, nhưng chất lượng ok.",
        avatar: "../Images/Contact/avatar.jpg",
        photos: ["../Images/Contact/Review2.jpg", "../Images/Contact/Review.jpg"],
        messages: ["Bạn có muốn đổi sản phẩm khác không?", "Giúp tôi đổi cái nhẹ hơn màu be đi"]
    },
    {
        id: "u03",
        name: "Bùi Công Nam",
        phone: "0903 111 222",
        product: "kính Râm R27",
        rating: 2,
        content: "Không giống hình, quá thất vọng.",
        avatar: "../Images/Contact/avatar.jpg",
        photos: ["../Images/Contact/Review.jpg", "../Images/Contact/Review2.jpg"],
        messages: ["Có kính nào làm tui ngầu hơn không?"]
    }
];

const $ = (s, c = document) => c.querySelector(s);
const $$ = (s, c = document) => [...c.querySelectorAll(s)];
const star = (n) =>
    '<i class="fa-solid fa-star"></i>'.repeat(n) +
    '<i class="fa-regular fa-star"></i>'.repeat(5 - n);

const reviewListEl = $("#reviewList");
let activeId = null;


function renderList(filter = "") {
    reviewListEl.innerHTML = "";

    users.forEach(u => {
        const item = document.createElement("article");
        item.className = "review-item";
        item.dataset.id = u.id;
        item.innerHTML = `
        <img class="r-avt" src="${u.avatar}" alt="${u.name}" />
        <div>
          <div class="r-head">
            <div class="r-name">${u.name}</div>
            <div class="stars">${star(u.rating)}</div>
          </div>
          <div class="r-prod"><strong>${u.product}</strong></div>
          <div class="r-body">${u.content}</div>
          <div class="r-phone">${u.phone}</div>
          ${u.photos && u.photos.length ? `<div class="r-photos">${u.photos.map(p => `<img src="${p}" alt="">`).join("")}</div>` : ""}
        </div>
      `;
        item.addEventListener("click", () => selectUser(u.id));
        reviewListEl.appendChild(item);
    });

    if (activeId) {
        const el = $(`.review-item[data-id="${activeId}"]`);
        if (el) el.classList.add("active");
    }
}


function selectUser(id) {
    activeId = id;
    $$(".review-item").forEach(el => el.classList.toggle("active", el.dataset.id === id));

    const u = users.find(x => x.id === id);
    if (!u) return;

    $("#chatAvatar").style.backgroundImage = `url("${u.avatar}")`;
    $("#chatTitle").textContent = u.name;
    $("#chatSub").textContent = `${u.product} · ${u.phone} · ${u.rating}★`;

    const body = $("#chatBody");
    body.innerHTML = "";
    u.messages.forEach(txt => {
        const row = document.createElement("div");
        row.className = "row";
        row.innerHTML = `<div><div class="msg">${escapeHtml(txt)}</div></div>`;
        body.appendChild(row);
    });
    body.scrollTop = body.scrollHeight;
}

$("#chatForm").addEventListener("submit", (e) => {
    e.preventDefault();
    const input = $("#chatText");
    const text = input.value.trim();
    if (!text || !activeId) return;

    const u = users.find(x => x.id === activeId);
    if (!u) return;

    u.messages.push(text);

    const body = $("#chatBody");
    const row = document.createElement("div");
    row.className = "row me";
    row.innerHTML = `<div><div class="msg">${escapeHtml(text)}</div></div>`;
    body.appendChild(row);
    body.scrollTop = body.scrollHeight;

    input.value = "";
});

renderList();

if (users[0]) selectUser(users[0].id);

function escapeHtml(str) {
    return String(str)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;");
}
