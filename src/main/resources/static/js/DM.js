    function ajaxResponse(data,content){
        $.ajax({
                 url: "/view/chat",
                 type: 'POST',
                 data: {target_id: data,
                        content: content},
            }).done(function (data){
                $('.direct_list').html(data);
            });
        }

    function sendMessage() {
                        const messageInput = document.getElementById('messageInput');
                        const message = messageInput.value.trim();
                        if (message !== '') {
                            const content = messageInput.value;
                            ajaxResponse(null, content);
                            document.querySelector('.direct_list').scrollTop = document.querySelector('.direct_list').scrollHeight;
                            messageInput.value = '';
                        }
                    }

    document.addEventListener('DOMContentLoaded', function () {
            document.querySelector('button[type="submit"]').addEventListener('click', sendMessage);

            document.getElementById('messageInput').addEventListener('keypress', function (e) {
                if (e.key === 'Enter') {
                    e.preventDefault();
                    sendMessage();
                }
            });


let data = ""; // 이전과 다른 채팅방을 골랐을 때, 비교하기 위함
let intervalId = ""; //
    document.querySelectorAll('.send_list').forEach(function (user) {


        user.addEventListener('click', function (event) {
            var clickedDiv = event.currentTarget;


            document.querySelectorAll('.send_list').forEach(function (div) {
                div.style.backgroundColor = '';
            });

            clickedDiv.style.backgroundColor = '#f0f0f0';
            var selectedUserId = event.currentTarget.querySelector('input[type="hidden"]').value;


            if(data == selectedUserId){                 // 같은 채팅방을 클릭했을 때,
                clearInterval(intervalId);

                data = selectedUserId;
                //intervalId.push(setInterval(ajaxResponse, 1000, data, null));
                ajaxResponse(data,null);
                intervalId = setInterval(ajaxResponse, 1000, data, null);


            } else if(selectedUserId != data){ // 이전과 다른 채팅방을 골랐을 때
                clearInterval(intervalId);

                data = selectedUserId;        // 새로 data 값(채팅방 고유 id) 등록
                ajaxResponse(data,null);
                intervalId = setInterval(ajaxResponse, 1000, data, null);
            } // else if(다른 페이지로 넘어 간다면, clearInterval() 할 수 있게)

            $(document).on('click', '.send_list', function() {
                var selectedUserId = $(this).find('input[type="hidden"]').val();
                var selectedUserName = $(this).find('.user_name').text();
                var selectedUserProfile = $(this).find('img').attr('src');

                var aboutSelectedUserDiv = $('.about_selected_user');
                var profileImgDiv = aboutSelectedUserDiv.find('.profile_img2');
                var imgElement = profileImgDiv.find('img');
                var userNameSpan = aboutSelectedUserDiv.find('.selected_user_name');

                imgElement.attr('src', selectedUserProfile);
                userNameSpan.text(selectedUserName);
            });

        });
    });
});


$(document).on('click', ".feed_footer_emoteButton .fa-face-smile", function (event) {
    // 현재 클릭된 .fa-face-smile이 포함된 .feed_footer_emoteButton 내부의 .emoticonbox 요소에 대한 작업 수행
    var emoticonBox = $(this).closest('.feed_footer_emoteButton').find('.emoticonbox');

    // 클릭 이벤트 전파 방지
    event.stopPropagation();

    // .emoticonbox를 토글
    emoticonBox.toggle();
});

// 문서의 다른 부분을 클릭했을 때 이벤트 전파 방지
$(document).on('click', function (event) {
    // 현재 클릭된 요소가 .fa-face-smile 또는 .emoticonbox 내부에 속한 요소가 아니라면 모든 .emoticonbox를 닫음
    if (!$(event.target).hasClass('fa-face-smile') && $(event.target).closest('.emoticonbox').length === 0) {
        $(".feed_footer_emoteButton .emoticonbox").hide();
    }
});


$(document).on('click', ".emoticon", function () {
    // 클릭된 .emoticon의 텍스트를 가져와서 댓글 입력란에 추가
    var emoticonText = $(this).text();
    var postCommentInput = $(this).closest(".custom-input").find("#messageInput")
    var currentText = postCommentInput.val();
     postCommentInput.val(currentText + emoticonText);

    // 댓글 입력란에 포커스 설정
    postCommentInput.focus();
});





