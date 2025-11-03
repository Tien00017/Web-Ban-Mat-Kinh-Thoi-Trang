let state = {
    fullName: "",
    email: "",
    phone: "",
    birthday: "",
    gender: "",
    address: "",
    avatarDataUrl: ""
};

// Thông báo Toast
function showToast(msg) {
    const el = document.getElementById("toast");
    el.textContent = msg;
    el.hidden = false;
    setTimeout(() => (el.hidden = true), 2000);
}

function applySummary(p) {
    const nameEl = document.getElementById("summaryName");
    const emailEl = document.getElementById("summaryEmail");
    const phoneEl = document.getElementById("summaryPhone");
    const avatarEl = document.getElementById("avatarPreview");

    nameEl.textContent = p.fullName || "Tên người dùng";
    emailEl.textContent = p.email || "email@example.com";
    phoneEl.textContent = p.phone || "(+84) 0123 456 789";

    avatarEl.src = p.avatarDataUrl || "../Images/ball.png";
}

function fillForm(p) {
    document.getElementById("fullName").value = p.fullName || "";
    document.getElementById("email").value = p.email || "";
    document.getElementById("phone").value = p.phone || "";
    document.getElementById("birthday").value = p.birthday || "";
    document.getElementById("gender").value = p.gender || "";
    document.getElementById("address").value = p.address || "";
}

function getFormData() {
    return {
        fullName: document.getElementById("fullName").value.trim(),
        email: document.getElementById("email").value.trim(),
        phone: document.getElementById("phone").value.trim(),
        birthday: document.getElementById("birthday").value,
        gender: document.getElementById("gender").value,
        address: document.getElementById("address").value.trim(),
        avatarDataUrl: state.avatarDataUrl
    };
}

function clearErrors() {
    document.querySelectorAll(".error").forEach(el => (el.textContent = ""));
}

function validateProfile(p) {
    let ok = true;
    clearErrors();

    const fullNameErr = document.querySelector('[data-for="fullName"]');
    const emailErr = document.querySelector('[data-for="email"]');
    const phoneErr = document.querySelector('[data-for="phone"]');

    if (!p.fullName) {
        fullNameErr.textContent = "Vui lòng nhập họ và tên.";
        ok = false;
    }
    if (!p.email) {
        emailErr.textContent = "Vui lòng nhập email.";
        ok = false;
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(p.email)) {
        emailErr.textContent = "Email không hợp lệ.";
        ok = false;
    }
    if (p.phone && !/^[\d\s()+-]{6,}$/.test(p.phone)) {
        phoneErr.textContent = "Số điện thoại không hợp lệ.";
        ok = false;
    }
    return ok;
}

function handleAvatarChange(file) {
    if (!file) return;
    const maxMB = 3;
    if (file.size > maxMB * 1024 * 1024) {
        showToast(`Ảnh quá lớn (>${maxMB}MB).`);
        return;
    }
    const reader = new FileReader();
    reader.onload = () => {
        state.avatarDataUrl = reader.result;
        applySummary(state);
        showToast("Đã cập nhật ảnh đại diện.");
    };
    reader.readAsDataURL(file);
}
function initTheme() {
    const btn = document.getElementById("themeToggle");
    if (!btn) return;
    // Mặc định: LIGHT
    document.body.classList.remove("dark");
    btn.innerHTML = '<i class="fas fa-moon"></i>';       // moon -> bấm để vào dark
    btn.setAttribute("aria-pressed", "false");
}

function toggleTheme() {
    const btn = document.getElementById("themeToggle");
    if (!btn) return;
    const isDark = document.body.classList.toggle("dark");
    btn.innerHTML = isDark ? '<i class="fas fa-sun"></i>' : '<i class="fas fa-moon"></i>';
    btn.setAttribute("aria-pressed", isDark ? "true" : "false");
}

function handlePasswordChange(e) {
    e.preventDefault();
    const currentPw = document.getElementById("currentPassword");
    const newPw = document.getElementById("newPassword");
    const confirmPw = document.getElementById("confirmPassword");
    const pwErr = document.querySelector('[data-for="password"]');

    pwErr.textContent = "";

    if (!newPw.value || !confirmPw.value) {
        pwErr.textContent = "Vui lòng nhập đầy đủ mật khẩu mới và nhập lại.";
        return;
    }
    if (newPw.value !== confirmPw.value) {
        pwErr.textContent = "Mật khẩu nhập lại không khớp.";
        return;
    }

    currentPw.value = "";
    newPw.value = "";
    confirmPw.value = "";
    showToast("Đã đổi mật khẩu.");
}

// Khởi động
document.addEventListener("DOMContentLoaded", () => {
    initTheme();
    applySummary(state);
    fillForm(state);
    const profileForm = document.getElementById("profileForm");
    profileForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const data = getFormData();
        if (!validateProfile(data)) return;
        state = { ...data };
        applySummary(state);
        showToast("Đã lưu thay đổi.");
    });

    const avatarInput = document.getElementById("avatar");
    avatarInput.addEventListener("change", (ev) => {
        const file = ev.target.files && ev.target.files[0];
        handleAvatarChange(file);
        ev.target.value = "";
    });

    // Toggle theme
    const themeToggleBtn = document.getElementById("themeToggle");
    if (themeToggleBtn) themeToggleBtn.addEventListener("click", toggleTheme);

    const signOutBtn = document.getElementById("signOut");
    if (signOutBtn) {
        signOutBtn.addEventListener("click", () => {
            if (confirm("Bạn có chắc muốn đăng xuất?")) {
                state = {
                    fullName: "",
                    email: "",
                    phone: "",
                    birthday: "",
                    gender: "",
                    address: "",
                    avatarDataUrl: ""
                };
                fillForm(state);
                applySummary(state);
                showToast("Đã đăng xuất.");
            }
        });
    }

    // Đổi mật khẩu
    const passwordForm = document.getElementById("passwordForm");
    if (passwordForm) passwordForm.addEventListener("submit", handlePasswordChange);
});
