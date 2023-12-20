let userInput = document.getElementById('userInput');
let registerButton = document.getElementById('register');

// 입력 필드의 내용을 확인하는 함수
let checkInput = () => {
    if (userInput.value.trim() === '') {
        // 입력 필드가 비어 있으면 버튼을 비활성화합니다.
        registerButton.disabled = true;
    } else {
        // 입력 필드에 값이 있으면 버튼을 활성화합니다.
        registerButton.disabled = false;
    }
};

// 입력 필드의 변경 사항을 모니터링하고 checkInput 함수를 호출합니다.
userInput.addEventListener('input', checkInput);