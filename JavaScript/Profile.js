const email = document.getElementById("email");
const emailError = email.nextElementSibling;

email.addEventListener("blur", () => {
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!pattern.test(email.value.trim())) {
        emailError.textContent = "Email không hợp lệ!";
    } else {
        emailError.textContent = "";
    }
});


const phone = document.getElementById("phone");
const phoneError = phone.nextElementSibling;

phone.addEventListener("blur", () => {
    const cleaned = phone.value.replace(/\s+/g, "");  // xoá khoảng trắng
    const pattern = /^0\d{9}$/; // bắt đầu bằng 0, tổng 10 số

    if (!pattern.test(cleaned)) {
        phoneError.textContent = "Số điện thoại không hợp lệ! (Phải có 10 số và bắt đầu bằng 0)";
    } else {
        phoneError.textContent = "";
    }
});

const pass = document.getElementById("newPassword");
const confirm = document.getElementById("confirmPassword");
const confirmError = confirm.nextElementSibling;
const changeBtn = document.getElementById("changePassBtn");
// nút Đổi mật khẩu

changeBtn.addEventListener("click", () => {
    if (confirm.value !== pass.value) {
        confirmError.textContent = "Mật khẩu không trùng!";
    } else {
        confirmError.textContent = "";
        alert("Đổi mật khẩu thành công!");
    }
});


const avatarInput = document.getElementById("avatarUpload");
const avatarImg = document.getElementById("avatarImg");

avatarInput.addEventListener("change", function () {
    const file = this.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = function (e) {
        avatarImg.src = e.target.result;
    };
    reader.readAsDataURL(file);
});


