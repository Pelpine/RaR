let currentIndex = 0;
let slideInterval;

function showSlide(index) {
    const slides = document.querySelectorAll('.carousel-item');
    const totalSlides = slides.length;

    if (index >= totalSlides) {
        currentIndex = 0;
    } else if (index < 0) {
        currentIndex = totalSlides - 1;
    } else {
        currentIndex = index;
    }

    const offset = -currentIndex * 100;
    const carouselInner = document.querySelector('.carousel-inner');
    carouselInner.style.transform = `translateX(${offset}%)`;

    slides.forEach((slide, idx) => {
        if (idx === currentIndex) {
            slide.classList.add('active');
        } else {
            slide.classList.remove('active');
        }
    });

    resetInterval();
}

function nextSlide() {
    showSlide(currentIndex + 1);
}

function prevSlide() {
    showSlide(currentIndex - 1);
}

function resetInterval() {
    clearInterval(slideInterval);
    slideInterval = setInterval(nextSlide, 4000);
}

// 초기 슬라이드 표시 및 타이머 설정
showSlide(currentIndex);
slideInterval = setInterval(nextSlide, 4000);

// 버튼 이벤트 핸들러 추가
document.querySelector('.next-button').addEventListener('click', nextSlide);
document.querySelector('.prev-button').addEventListener('click', prevSlide);
