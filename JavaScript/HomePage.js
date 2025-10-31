const track = document.querySelector('.news-track');
const slides = Array.from(track.children);
const nextButton = document.querySelector('.next');
const prevButton = document.querySelector('.prev');

let currentIndex = 0;

function showSlide(index) {
    if (index < 0) index = slides.length - 1;
    if (index >= slides.length) index = 0;
    track.style.transform = `translateX(-${index * 100}%)`;
    currentIndex = index;
}

nextButton.addEventListener('click', () => {
    showSlide(currentIndex + 1);
});

prevButton.addEventListener('click', () => {
    showSlide(currentIndex - 1);
});

// Tự động chuyển slide mỗi 5 giây
setInterval(() => {
    showSlide(currentIndex + 1);
}, 5000);
