$('.profile_nav_load').load('/view/navbar');

function toggleContent(category) {
    if (category === 'post') {
        $('#home_feed_container').show();
        $('.saved-board').hide();
    } else if (category === 'saved') {
        $('#home_feed_container').hide();
        $('.saved-board').show();
    }
}

$('#postLink').click(function() {
    toggleContent('post');
    $('.post_box').css('border-top', '2px solid black');
    $('.saved_box').css('border', 'none');
});

$('#savedLink').click(function() {
    toggleContent('saved');
    $('.saved_box').css('border-top', '2px solid black');
    $('.post_box').css('border', 'none');
});


//현재 스크롤 위치 저장
let lastScroll = 0;
let page = 0;
let nowPageLimit = 0;
let nextPageLimit = 0;
let currentScroll = 0
let documentHeight = 0
let nowHeight = 0
let isLoading =false;
//데이터 가져오는 함수


$(document).on('click', ".fa-heart", function() {
    var postId = $(this).data("post-id");
    var className = $(this).attr('class');
    var $this = $(this);

    // 좋아요 수를 나타내는 요소를 찾음
    var likeNumElement = $('#likeCount_' + postId);

    if (className === "fa-solid fa-heart") {
          $.ajax({
                    type: 'GET',
                    url: '/cancelLikeProfile',//'/post/cancelLike' '/cancelLikeProfile'
                    data: {
                        postId: postId,
                    },
                    success: function(response) {
                      if (response.success) {
                         alert(response.message);
                         likeNumElement.text(response.count);
                         $this.prop('class', 'fa-regular fa-heart');
                         $this.css("color", "black");

                         $("#home_feed_box2 > div").empty();
                         $("#home_feed_box2 > div").append(response.htmlpost);
                         $("#home_feed_bookmark_box2 > div").empty();
                         $("#home_feed_bookmark_box2 > div").append(response.htmlbookmark);
                      }
                    },
                    error: function(error) {
                        // 오류가 발생한 경우에 대한 처리
                        console.log(error);
                    }
                });
    } else if (className === "fa-regular fa-heart") {
           $.ajax({
                 type: 'GET',
                 url:  '/registerLikeProfile', // '/post/registerLike' '/registerLikeProfile'
                 data: {
                     postId: postId,
                 },
                 success: function(response) {
                   if (response.success) {
                      alert(response.message)
                      likeNumElement.text(response.count);
                      $this.prop('class', 'fa-solid fa-heart');
                      $this.css("color", "red");

                      $("#home_feed_box2 > div").empty();
                      $("#home_feed_box2 > div").append(response.htmlpost);
                      $("#home_feed_bookmark_box2 > div").empty();
                      $("#home_feed_bookmark_box2 > div").append(response.htmlbookmark);
                   }
                 },
                 error: function(error) {
                     // 오류가 발생한 경우에 대한 처리
                     console.log(error);
                 }
             });
    }
});

$(document).on('click', ".fa-bookmark", function() {
    var postId = $(this).data("post-id");
    var className = $(this).attr('class');
    var $this = $(this);

    if (className === "fa-solid fa-bookmark") {
          $.ajax({
                    type: 'GET',
                    url: '/registerBookmark', //'/bookmark/registerBookmark' '/registerBookmark'
                    data: {
                        postId: postId,
                    },
                    success: function(response) {
                      if (response.success) {
                         alert(response.message);
                         //alert(response.html);
                         $("#home_feed_box2 > div").empty();
                         $("#home_feed_box2 > div").append(response.htmlpost);
                         $("#home_feed_bookmark_box2 > div").empty();
                         $("#home_feed_bookmark_box2 > div").append(response.htmlbookmark);

                         $this.prop('class', 'fa-regular fa-bookmark');

                      }
                    },
                    error: function(error) {
                        // 오류가 발생한 경우에 대한 처리
                        console.log(error);
                    }
                });
    } else if (className === "fa-regular fa-bookmark") {
           $.ajax({
                 type: 'GET',
                 url: '/registerBookmark' , //'/bookmark/registerBookmark' '/registerBookmark'
                 data: {
                     postId: postId,
                 },
                 success: function(response) {
                   if (response.success) {
                      alert(response.message);
                      //alert(response.html);
                      $("#home_feed_box2 > div").empty();
                      $("#home_feed_box2 > div").append(response.htmlpost);
                      $("#home_feed_bookmark_box2 > div").empty();
                      $("#home_feed_bookmark_box2 > div").append(response.htmlbookmark);
                      $this.prop('class', 'fa-solid fa-bookmark');

                   }
                 },
                 error: function(error) {
                     // 오류가 발생한 경우에 대한 처리
                     console.log(error);
                 }
             });
    }
});


$(document).on("click",".post-board",function() {
    window.location.href = '/view/registPost';
})