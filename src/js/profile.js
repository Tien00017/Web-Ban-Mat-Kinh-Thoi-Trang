// // ========== JS: script.js ==========
//
// // Helpers
// const $ = (sel, ctx = document) => ctx.querySelector(sel);
// const $$ = (sel, ctx = document) => Array.from(ctx.querySelectorAll(sel));
//
// const storageKey = "userProfile";
// const themeKey = "themePreference";
//
// const defaultProfile = {
//     fullName: "Tên người dùng",
//     email: "email@example.com",
//     phone: "(+84) 0123 456 789",
//     birthday: "",
//     gender: "",
//     address: "",
//     newsletter: true,
//     avatarDataUrl: "" // base64 image
// };
//
// function loadProfile() {
//     try {
//         const raw = localStorage.getItem(storageKey);
//         return raw ? JSON.parse(raw) : {...defaultProfile};
//     } catch {
//         return {...defaultProfile};
//     }
// }
//
// function saveProfile(data) {
//     localStorage.setItem(storageKey, JSON.stringify(data));
// }
//
// function showToast(msg) {
//     const toast = $("#toast");
//     toast.textContent = msg;
//     toast.hidden = false;
//     setTimeout(() => (toast.hidden = true), 2200);
// }
//
// function applySummary(p) {
//     $("#summaryName").textContent = p.fullName || "Tên người dùng";
//     $("#summaryEmail").textContent = p.email || "email@example.com";
//     $("#summaryPhone").textContent = p.phone || "(+84) 0123 456 789";
//
//     const avatar = $("#avatarPreview");
//     if (p.avatarDataUrl) {
//         avatar.src = p.avatarDataUrl;
//     } else {
//         // Placeholder SVG theo chữ cái đầu tên
//         const initials = (p.fullName || "User")
//             .split(" ")
//             .map(x => x[0]?.toUpperCase())
//             .slice(0, 2)
//             .join("");
//         const svg = encodeURIComponent(`
//       <svg xmlns='http://www.w3.org/2000/svg' width='200' height='200'>
//         <defs>
//           <linearGradient id='g' x1='0' y1='0' x2='1' y2='1'>
//             <stop offset='0%' stop-color='#3b6cff'/>
//             <stop offset='100%' stop-color='#00d0ff'/>
//           </linearGradient>
//         </defs>
//         <rect width='100%' height='100%' fill='url(#g)'/>
//         <text x='50%' y='54%' dominant-baseline='middle' text-anchor='middle'
//               font-family='system-ui,Segoe UI,Roboto,Helvetica,Arial' font-weight='700' font-size='84' fill='white'>
//           ${initials || "U"}
//         </text>
//       </svg>
//     `);
//         avatar.src = `data:image/svg+xml,${svg}`;
//     }
// }
//
// function fillForm(p) {
//     $("#fullName").value = p.fullName || "";
//     $("#email").value = p.email || "";
//     $("#phone").value = p.phone || "";
//     $("#birthday").value = p.birthday || "";
//     $("#gender").value = p.gender || "";
//     $("#address").value = p.address || "";
//     $("#newsletter").checked = !!p.newsletter;
// }
//
// function getFormData() {
//     return {
//         fullName: $("#fullName").value.trim(),
//         email: $("#email").value.trim(),
//         phone: $("#phone").value.trim(),
//         birthday: $("#birthday").value,
//         gender: $("#gender").value,
//         address: $("#address").value.trim(),
//         newsletter: $("#newsletter").checked,
//         avatarDataUrl: loadProfile().avatarDataUrl // giữ ảnh hiện có nếu chưa đổi
//     };
// }
//
// function validateProfile(p) {
//     let ok = true;
//
//     // Clear errors
//     $$(".error").forEach(e => (e.textContent = ""));
//
//     if (!p.fullName) {
//         $('[data-for="fullName"]').textContent = "Vui lòng nhập họ và tên.";
//         ok = false;
//     }
//     if (!p.email) {
//         $('[data-for="email"]').textContent = "Vui lòng nhập email.";
//         ok = false;
//     } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(p.email)) {
//         $('[data-for="email"]').textContent = "Email không hợp lệ.";
//         ok = false;
//     }
//     if (p.phone && !/^[\d\s()+-]{6,}$/.test(p.phone)) {
//         $('[data-for="phone"]').textContent = "Số điện thoại không hợp lệ.";
//         ok = false;
//     }
//     return ok;
// }
//
// function handleAvatarChange(file) {
//     if (!file) return;
//     const maxMB = 3;
//     if (file.size > maxMB * 1024 * 1024) {
//         showToast(`Ảnh quá lớn (>${maxMB}MB).`);
//         return;
//     }
//     const reader = new FileReader();
//     reader.onload = () => {
//         const prof = loadProfile();
//         prof.avatarDataUrl = reader.result;
//         saveProfile(prof);
//         applySummary(prof);
//         showToast("Đã cập nhật ảnh đại diện.");
//     };
//     reader.readAsDataURL(file);
// }
//
// function initTheme() {
//     const saved = localStorage.getItem(themeKey);
//     const prefersLight = window.matchMedia("(prefers-color-scheme: light)").matches;
//     const mode = saved || (prefersLight ? "light" : "dark");
//     document.documentElement.classList.toggle("light", mode === "light");
//     const btn = $("#themeToggle");
//     btn.textContent = mode === "light" ? "🌙" : "☀️";
//     btn.setAttribute("aria-pressed", mode === "light" ? "true" : "false");
// }
//
// function toggleTheme() {
//     const isLight = document.documentElement.classList.toggle("light");
//     localStorage.setItem(themeKey, isLight ? "light" : "dark");
//     $("#themeToggle").textContent = isLight ? "🌙" : "☀️";
//     $("#themeToggle").setAttribute("aria-pressed", isLight ? "true" : "false");
// }
//
// // Password form (demo)
// function handlePasswordChange(e) {
//     e.preventDefault();
//     const newPw = $("#newPassword").value;
//     const confirmPw = $("#confirmPassword").value;
//     const err = $('[data-for="password"]');
//
//     err.textContent = "";
//     if (!newPw || newPw.length < 8 ||
//         !/[a-z]/.test(newPw) || !/[A-Z]/.test(newPw) || !/\d/.test(newPw)) {
//         err.textContent = "Mật khẩu chưa đủ mạnh.";
//         return;
//     }
//     if (newPw !== confirmPw) {
//         err.textContent = "Mật khẩu nhập lại không khớp.";
//         return;
//     }
//     // Demo only – không gọi API.
//     $("#currentPassword").value = "";
//     $("#newPassword").value = "";
//     $("#confirmPassword").value = "";
//     showToast("Đã đổi mật khẩu.");
// }
//
// document.addEventListener("DOMContentLoaded", () => {
//     initTheme();
//
//     const prof = loadProfile();
//     applySummary(prof);
//     fillForm(prof);
//
//     // Lưu form
//     $("#profileForm").addEventListener("submit", (e) => {
//         e.preventDefault();
//         const data = getFormData();
//         if (!validateProfile(data)) return;
//         saveProfile(data);
//         applySummary(data);
//         showToast("Đã lưu thay đổi.");
//     });
//
//     // Khôi phục từ localStorage (hoặc mặc định)
//     $("#resetBtn").addEventListener("click", () => {
//         const p = loadProfile();
//         fillForm(p);
//         applySummary(p);
//         showToast("Đã khôi phục thông tin.");
//     });
//
//     // Upload avatar
//     $("#avatar").addEventListener("change", (ev) => {
//         const f = ev.target.files?.[0];
//         handleAvatarChange(f);
//         ev.target.value = "";
//     });
//
//     // Đổi theme
//     $("#themeToggle").addEventListener("click", toggleTheme);
//
//     // Đăng xuất (demo)
//     $("#signOut").addEventListener("click", () => {
//         if (confirm("Bạn có chắc muốn đăng xuất?")) {
//             localStorage.removeItem(storageKey);
//             fillForm(defaultProfile);
//             applySummary(defaultProfile);
//             showToast("Đã đăng xuất (demo).");
//         }
//     });
//
//     // Đổi mật khẩu
//     $("#passwordForm").addEventListener("submit", handlePasswordChange);
// });
