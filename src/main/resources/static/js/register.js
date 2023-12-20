
$(function() {
    $('#register').on('click', function() {
        // 사용자가 입력한 데이터 가져오기
        var phoneOrEmail = $('#phoneOrEmail').val();
        var name = $('#name').val();
        var username = $('#username').val();
        var password = $('#password').val();

        // 값이 비어 있는지 또는 null인지 확인
        if (!phoneOrEmail || !name || !username || !password) {
            alert("모든 항목을 입력해주세요.");
            return;
        }

        // 데이터를 서버로 전송 (Ajax를 사용)
        $.ajax({
            type: 'POST',
            url: '/method/registUser',
            data: {
                phoneOrEmail: phoneOrEmail,
                name: name,
                username: username,
                password: password
            },
            success: function(data) {
                if (data.success) {
                    alert(data.message); // 등록 성공 시 메시지 표시
                    window.location.href = '/loginTest';
                } else {
                    alert(data.message); // 등록 실패 시 메시지 표시
                    $('#phoneOrEmail').val("");
                    $('#name').val("");
                    $('#username').val("");
                    $('#password').val("");
                }
            },
            error: function(error) {
               // 등록 실패 시
               alert('등록 연결이 어렵습니다.');
               $('#phoneOrEmail').val("")
               $('#name').val("");
               $('#username').val("");
               $('#password').val("");
            }
        });
    });
});

