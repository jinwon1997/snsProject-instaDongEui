document.addEventListener("DOMContentLoaded", function () {
    const images = document.querySelectorAll(".fade_img");
    let currentIndex = 0;
    images[0].style.opacity = 1;
    images[0].style.transition = "opacity 2s ease-in-out";

    function nextImage() {

        for (let i = 0; i < images.length; i++) {
            images[i].style.opacity = 0;
        }
        currentIndex = (currentIndex + 1) % images.length;

        images[currentIndex].style.transition = "opacity 2s ease-in-out";
        images[currentIndex].style.opacity = 1;
    }

    setInterval(nextImage, 5000);
});

$(function() {
    $('#login').on('click', function() {
        // 사용자가 입력한 데이터 가져오기
        var id = $('#id').val();
        var password = $('#password').val();

        // 값이 비어 있는지 또는 null인지 확인
        if (!id || !password) {
            alert("모든 항목을 입력해주세요.");
            return;
        }
         $("form[name='loginForm']").submit();
    });
});
