// ===== JS đơn giản: KHÔNG dùng localStorage =====

// Trợ giúp DOM
const $  = (sel, ctx = document) => ctx.querySelector(sel);
const $$ = (sel, ctx = document) => Array.from(ctx.querySelectorAll(sel));

// Trạng thái tạm trong bộ nhớ (mất khi reload F5)
let state = {
    fullName: "",
    email: "",
    phone: "",
    birthday: "",
    gender: "",
    address: "",
    newsletter: true,
    avatarDataUrl: "" // data URL ảnh tạm để preview
};

// Toast
function showToast(msg) {
    const el = $("#toast");
    el.textContent = msg;
    el.hidden = false;
    setTimeout(() => (el.hidden = true), 2000);
}

// Cập nhật thẻ tóm tắt (card bên trái)
function applySummary(p) {
    $("#summaryName").textContent  = p.fullName || "Tên người dùng";
    $("#summaryEmail").textContent = p.email   || "email@example.com";
    $("#summaryPhone").textContent = p.phone   || "(+84) 0123 456 789";

    const avatar = $("#avatarPreview");
    if (p.avatarDataUrl) {
        avatar.src = p.avatarDataUrl;
    } else {
        // Avatar SVG theo chữ cái đầu
        const initials = (p.fullName || "User")
            .split(" ").map(x => x[0]?.toUpperCase()).slice(0, 2).join("");
        const svg = encodeURIComponent(`
      <svg xmlns='http://www.w3.org/2000/svg' width='200' height='200'>
        <defs>
          <linearGradient id='g' x1='0' y1='0' x2='1' y2='1'>
            <stop offset='0%' stop-color='#3b6cff'/>
            <stop offset='100%' stop-color='#00d0ff'/>
          </linearGradient>
        </defs>
        <rect width='100%' height='100%' fill='url(#g)'/>
        <text x='50%' y='54%' dominant-baseline='middle' text-anchor='middle'
              font-family='system-ui,Segoe UI,Roboto,Helvetica,Arial' font-weight='700' font-size='84' fill='white'>
          ${initials || "U"}
        </text>
      </svg>
    `);
        avatar.src = `data:image/svg+xml,${svg}`;
    }
}

// Đổ form theo state
function fillForm(p) {
    $("#fullName").value = p.fullName || "";
    $("#email").value    = p.email    || "";
    $("#phone").value    = p.phone    || "";
    $("#birthday").value = p.birthday || "";
    $("#gender").value   = p.gender   || "";
    $("#address").value  = p.address  || "";
    $("#newsletter").checked = !!p.newsletter;
}

// Lấy dữ liệu từ form
function getFormData() {
    return {
        fullName: $("#fullName").value.trim(),
        email:    $("#email").value.trim(),
        phone:    $("#phone").value.trim(),
        birthday: $("#birthday").value,
        gender:   $("#gender").value,
        address:  $("#address").value.trim(),
        newsletter: $("#newsletter").checked,
        avatarDataUrl: state.avatarDataUrl // giữ ảnh hiện tại
    };
}

// Validate cơ bản
function validateProfile(p) {
    let ok = true;
    $$(".error").forEach(e => (e.textContent = ""));

    if (!p.fullName) {
        $('[data-for="fullName"]').textContent = "Vui lòng nhập họ và tên.";
        ok = false;
    }
    if (!p.email) {
        $('[data-for="email"]').textContent = "Vui lòng nhập email.";
        ok = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(p.email)) {
        $('[data-for="email"]').textContent = "Email không hợp lệ.";
        ok = false;
    }
    if (p.phone && !/^[\d\s()+-]{6,}$/.test(p.phone)) {
        $('[data-for="phone"]').textContent = "Số điện thoại không hợp lệ.";
        ok = false;
    }
    return ok;
}

// Đổi ảnh: chỉ preview tạm (không lưu qua F5)
function handleAvatarChange(file) {
    if (!file) return;
    const maxMB = 3;
    if (file.size > maxMB * 1024 * 1024) {
        showToast(`Ảnh quá lớn (>${maxMB}MB).`);
        return;
    }
    const reader = new FileReader();
    reader.onload = () => {
        state.avatarDataUrl = reader.result;    // chỉ lưu vào biến state
        applySummary(state);                    // cập nhật preview
        showToast("Đã cập nhật ảnh đại diện.");
    };
    reader.readAsDataURL(file);
}

// Theme: chỉ toggle trên UI, KHÔNG nhớ lại sau F5
function initTheme() {
    // Mặc định dark; nếu người dùng máy đặt light, có thể tôn trọng cài đặt hệ điều hành:
    const prefersLight = window.matchMedia("(prefers-color-scheme: light)").matches;
    document.documentElement.classList.toggle("light", prefersLight);
    $("#themeToggle").textContent = prefersLight ? "🌙" : "☀️";
    $("#themeToggle").setAttribute("aria-pressed", prefersLight ? "true" : "false");
}
function toggleTheme() {
    const isLight = document.documentElement.classList.toggle("light");
    $("#themeToggle").textContent = isLight ? "🌙" : "☀️";
    $("#themeToggle").setAttribute("aria-pressed", isLight ? "true" : "false");
}

// Đổi mật khẩu (demo)
function handlePasswordChange(e) {
    e.preventDefault();
    const newPw = $("#newPassword").value;
    const confirmPw = $("#confirmPassword").value;
    const err = $('[data-for="password"]');

    err.textContent = "";
    if (!newPw || newPw.length < 8 ||
        !/[a-z]/.test(newPw) || !/[A-Z]/.test(newPw) || !/\d/.test(newPw)) {
        err.textContent = "Mật khẩu chưa đủ mạnh.";
        return;
    }
    if (newPw !== confirmPw) {
        err.textContent = "Mật khẩu nhập lại không khớp.";
        return;
    }
    // Demo: chỉ reset input & báo
    $("#currentPassword").value = "";
    $("#newPassword").value    = "";
    $("#confirmPassword").value= "";
    showToast("Đã đổi mật khẩu (demo).");
}

// Khởi động
document.addEventListener("DOMContentLoaded", () => {
    initTheme();

    // Đổ giao diện từ state mặc định
    applySummary(state);
    fillForm(state);

    // Lưu form (chỉ update state + UI, không lưu bền)
    $("#profileForm").addEventListener("submit", (e) => {
        e.preventDefault();
        const data = getFormData();
        if (!validateProfile(data)) return;
        state = { ...data };     // cập nhật biến state
        applySummary(state);     // cập nhật card tóm tắt
        showToast("Đã lưu thay đổi (tạm).");
    });

    // Khôi phục: đưa form & card về state hiện tại (hoặc mặc định)
    $("#resetBtn").addEventListener("click", () => {
        fillForm(state);
        applySummary(state);
        showToast("Đã khôi phục thông tin (tạm).");
    });

    // Upload avatar
    $("#avatar").addEventListener("change", (ev) => {
        const f = ev.target.files?.[0];
        handleAvatarChange(f);
        ev.target.value = ""; // cho phép chọn lại cùng file
    });

    // Đổi theme (không lưu)
    $("#themeToggle").addEventListener("click", toggleTheme);

    // Đăng xuất (demo): chỉ đưa về mặc định trong RAM
    $("#signOut").addEventListener("click", () => {
        if (confirm("Bạn có chắc muốn đăng xuất?")) {
            state = {
                fullName: "Tên người dùng",
                email: "email@example.com",
                phone: "(+84) 0123 456 789",
                birthday: "",
                gender: "",
                address: "",
                newsletter: true,
                avatarDataUrl: ""
            };
            fillForm(state);
            applySummary(state);
            showToast("Đã đăng xuất (demo, không lưu).");
        }
    });

    // Đổi mật khẩu
    $("#passwordForm").addEventListener("submit", handlePasswordChange);
});
