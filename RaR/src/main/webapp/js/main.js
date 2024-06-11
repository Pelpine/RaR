let currentIndex = 0;

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
}

function nextSlide() {
    showSlide(currentIndex + 1);
}

function prevSlide() {
    showSlide(currentIndex - 1);
}

// 일정 시간 간격으로 슬라이드 넘기기 (4초마다 다음 슬라이드)
setInterval(nextSlide, 4000);

// 초기 슬라이드 표시
showSlide(currentIndex);