const tabs = document.querySelectorAll(".tab");
const contents = document.querySelectorAll(".tab-content");

tabs.forEach(tab => {
    tab.addEventListener("click", () => {

        tabs.forEach(t => t.classList.remove("active"));
        contents.forEach(c => c.classList.remove("active"));

        tab.classList.add("active");
        document.getElementById(tab.dataset.tab).classList.add("active");
    });
});


const backToTopBtn = document.getElementById("backToTop");

window.addEventListener("scroll", () => {
    if (window.scrollY > 300) {
        backToTopBtn.style.display = "flex";
    } else {
        backToTopBtn.style.display = "none";
    }
});

backToTopBtn.addEventListener("click", () => {
    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
});

const stars = document.querySelectorAll(".rating-input .stars i");
const ratingText = document.querySelector(".rating-text");
const ratingValue = document.getElementById("ratingValue");

let selectedRating = 0;

stars.forEach(star => {
    const value = parseInt(star.dataset.value);

    star.addEventListener("mouseenter", () => {
        highlightStars(value);
        ratingText.textContent = value + " sao";
    });

    star.addEventListener("click", () => {
        selectedRating = value;
        ratingValue.value = value;
        ratingText.textContent = value + " sao";
    });

    star.addEventListener("mouseleave", () => {
        highlightStars(selectedRating);
        ratingText.textContent = selectedRating
            ? selectedRating + " sao"
            : "0 sao";
    });
});

function highlightStars(count) {
    stars.forEach(star => {
        star.classList.toggle(
            "active",
            parseInt(star.dataset.value) <= count
        );
    });
}