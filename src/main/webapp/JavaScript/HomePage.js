const track = document.querySelector('.news-track');
const slides = Array.from(track.children);

const nextButton = document.querySelector('.next');
const prevButton = document.querySelector('.prev');

const dotsContainer = document.querySelector('.slider-dots');

let currentIndex = 0;

/* ===== TẠO DẤU CHẤM ===== *   /
slides.forEach((_, i) => {
    const dot = document.createElement("span");
    dot.classList.add("dot");
    if (i === 0) dot.classList.add("active");
    dot.addEventListener("click", () => showSlide(i));
    dotsContainer.appendChild(dot);
});

const dots = document.querySelectorAll(".dot");

/* ===== HÀM HIỂN THỊ SLIDE ===== */
function showSlide(index) {
    if (index < 0) index = slides.length - 1;
    if (index >= slides.length) index = 0;

    track.style.transform = `translateX(-${index * 100}%)`;
    currentIndex = index;

    dots.forEach(d => d.classList.remove("active"));
    dots[index].classList.add("active");
}

/* ===== NÚT NEXT / PREV ===== */
nextButton.addEventListener("click", () => showSlide(currentIndex + 1));
prevButton.addEventListener("click", () => showSlide(currentIndex - 1));

/* ===== TỰ ĐỘNG CHUYỂN SLIDE ===== */
setInterval(() => {
    showSlide(currentIndex + 1);
}, 5000);
