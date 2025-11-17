let state = {
    fullName: "",
    email: "",
    phone: "",
    birthday: "",
    gender: "",
    address: "",
    avatarDataUrl: ""
};

function applySummary(p) {
    const avatarEl = document.getElementById("avatarPreview");
    if (!avatarEl) return;
    avatarEl.src = p.avatarDataUrl || "../Images/Profile/ball.png";
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
    }
    else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(p.email)) {
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
    if (file.size > maxMB * 1024 * 1024) return;

    const reader = new FileReader();
    reader.onload = () => {
        state.avatarDataUrl = reader.result;
        applySummary(state);
    };
    reader.readAsDataURL(file);
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
}

document.addEventListener("DOMContentLoaded", () => {
    if (typeof initTheme === "function") initTheme();

    applySummary(state);
    fillForm(state);

    const profileForm = document.getElementById("profileForm");
    if (profileForm) {
        profileForm.addEventListener("submit", (e) => {
            e.preventDefault();
            const data = getFormData();
            if (!validateProfile(data)) return;

            state = { ...data };
            applySummary(state);
        });
    }

    const avatarInput = document.getElementById("avatar");
    if (avatarInput) {
        avatarInput.addEventListener("change", (ev) => {
            const file = ev.target.files && ev.target.files[0];
            handleAvatarChange(file);
            ev.target.value = "";
        });
    }

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
            }
        });
    }

    const passwordForm = document.getElementById("passwordForm");
    if (passwordForm) {
        passwordForm.addEventListener("submit", handlePasswordChange);
    }
});
