let previousImageURL = '';

function openFileInput() {
  document.getElementById('profileImageInput').click();
}

function changeProfileImage(event) {
  const fileInput = event.target;
  const profileImage = document.querySelector('.hide img');

  if (fileInput.files && fileInput.files[0]) {
    const reader = new FileReader();

    reader.onload = function (e) {
      profileImage.src = e.target.result;
    };

    reader.readAsDataURL(fileInput.files[0]);
  } else {
    profileImage.src = previousImageURL;
  }
}

//changeProfileImage({ target: document.getElementById('profileImageInput') });

document.getElementById('profileImageInput').addEventListener('change', changeProfileImage);

$(".right-submit").click(function () {
  var fileInput = $(this).closest(".change-info").find(".tfile");
  var selectedFile = fileInput[0].files[0];

  if (!selectedFile) {
    $(this).closest(".change-info").submit();
  } else {
    var allowedExtensions = ["png", "jpg", "jpeg"];
    var fileExtension = selectedFile.name.split('.').pop().toLowerCase();

    if (allowedExtensions.indexOf(fileExtension) > -1) {
      console.log("선택한 파일 경로:", selectedFile.name);

      var formData = new FormData();
      formData.append('file', selectedFile);
      formData.append('introduce', $(this).closest(".change-info").find(".right-intro").val());
      formData.append('gender', $(this).closest(".change-info").find('input[name="gender"]:checked').val());

      $.ajax({
        url: '/view/profile_ch2',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(response) {
          if (response.success){
            alert(response.message);
            location.href = "/view/home";
          } else {
            alert(response.message);
          }
        },
        error: function(error) {
          console.log("에러 발생");
        }
      });
    } else {
      alert("허용되지 않는 파일 형식입니다.");
    }
  }
});


const radioButtons = document.querySelectorAll('input[type="radio"]');

function handleRadioClick(event) {
  radioButtons.forEach(button => {
    if (button !== event.target) {
      button.checked = false;
    }
  });
}

radioButtons.forEach(button => {
  button.addEventListener('click', handleRadioClick);
});

function countCharacters(textarea) {
  const maxLength = parseInt(textarea.getAttribute('maxlength'));
  const currentLength = textarea.value.length;
  const charCount = document.getElementById('charCount');

  charCount.textContent = currentLength + ' / ' + maxLength;

  if (currentLength > maxLength) {
    textarea.value = textarea.value.substring(0, maxLength);
    charCount.textContent = maxLength + ' / ' + maxLength;
  }
}
