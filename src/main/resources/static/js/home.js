$('.nav_load').load('/view/navbar');
//현재 스크롤 위치 저장
let lastScroll = 0;
let page = 0;
let nowPageLimit = 0;
let nextPageLimit = 0;
let currentScroll = 0
let documentHeight = 0
let nowHeight = 0
let isLoading =false;

function getData(limit){
    if (isLoading) {
        return;
    }

	nextPageLimit = (page + 1) * limit;

    isLoading = true

	$.ajax({
		type: "GET",
		url: '/test/load-more-data',
		data: {
			 "nextPageLimit" : nextPageLimit,
			 "limit" : limit
		},
		success: function(data){
		    if (data.length > 0 ) {
		        $("#home_feed_box2 > div").append(data);
		    }
		},
		error: function (data, status, err) {
			page = page;
		}, complete: function(){
		    isLoading = false;
			page = page + 1;
		}
	});
}

$(document).scroll(function(e){

    currentScroll = $(this).scrollTop();

    documentHeight = $(document).height();


    nowHeight = $(this).scrollTop() + $(window).height();



    if(currentScroll > lastScroll){


        if(documentHeight < (nowHeight + (documentHeight*0.1))){
            getData(8);
        }
    }

    lastScroll = currentScroll;
});

$(document).on("click", ".recommend_follow", function() {
        var $this = $(this);

        var followId = $(this).data("follow-id");
        var buttonText = $(this).text();

        if ($.trim(buttonText) === "팔로우") {
           $.ajax({
           		type: "GET",
           		url: '/follow/followUser',
           		data: {
           			 "followId" : followId,
           		},
           		success: function(data){
           		    if (data.success) {
           		        $this.text("팔로잉");
           		        $this.css("color", "black");
           		    }
           		    alert(data.message)
           		},
           		error: function (data) {
           		    console.error("에러 발생:", data);
           		}
           	});
        } else {

            $.ajax({
                    type: "GET",
                    url: '/follow/followCancel',
                    data: {
                         "followId" : followId,
                    },
                    success: function(data){
                        if (data.success) {
                            $this.text("팔로우");
                            $this.css("color", "deepskyblue");
                        }
                        alert(data.message)
                    },
                    error: function (data) {
                        console.error("에러 발생:", data);
                    }
                });
        }
});

$(document).on('keypress', '.feed_footer_postComment1', function(event) {
    if (event.which === 13) {
        event.preventDefault();


        var comment = $(this).val();
        var postId = $(this).closest('form').find('input[type="hidden"]').val();

        if(!comment.trim()) {
            return
        }
        $.ajax({
            type: 'GET',
            url: '/comment/registerComment',
            data: {
                comment: comment,
                postId: postId
            },
            success: function(response) {

              if (response.success) {
                 var newCommentSize = response.count;
                 var articleId = 'Post' + postId;
                  var html = `
                                                     <div class="explore_feed_head_box">
                                                         <div class="explore_feed_head_imgbox">
                                                             <a href="#" class="explore_feed_head_img">
                                                                 <img src="${response.append.url}" alt="">
                                                             </a>
                                                         </div>
                                                         <div class="explore_feed_infobox">
                                                             <div class="explore_feed_infobox1">${response.append.name}</div>
                                                             <div class="explore_feed_infobox2">${response.append.content}</div>
                                                             <div class="explore_feed_infobox2">${response.append.date}</div>
                                                         </div>
                                                     </div>
                                                 `;
                                                 $('.explore_feed_scrolls').append(html);

                 $('#' + articleId + ' .feed_footer_commentNum').text(newCommentSize);
              }
            },
            error: function(error) {
                console.log(error);
            }
        });


        $(this).val('');
    }
});

$(document).on('click', ".fa-heart", function() {
    var postId = $(this).data("post-id");
    var className = $(this).attr('class');
    var $this = $(this);


    var likeNumElement = $('#likeCount_' + postId);
    var likeNumElement2 = $('#likeCount2_' + postId);
    if (className === "fa-solid fa-heart") {
          $.ajax({
                    type: 'GET',
                    url: '/post/cancelLike',
                    data: {
                        postId: postId,
                    },
                    success: function(response) {
                      if (response.success) {
                         alert(response.message);
                         likeNumElement.text(response.count);
                         likeNumElement2.text(response.count)
                         $('span.like-icon i[data-post-id="' + postId + '"]').prop('class', 'fa-regular fa-heart');
                          $('span.like-icon i[data-post-id="' + postId + '"]').css("color", "black");
                      }
                    },
                    error: function(error) {
                        console.log(error);
                    }
                });
    } else if (className === "fa-regular fa-heart") {
           $.ajax({
                 type: 'GET',
                 url: '/post/registerLike',
                 data: {
                     postId: postId,
                 },
                 success: function(response) {
                   if (response.success) {
                      alert(response.message)
                      likeNumElement.text(response.count);
                       likeNumElement2.text(response.count)
                       $('span.like-icon i[data-post-id="' + postId + '"]').prop('class', 'fa-solid fa-heart');
                       $('span.like-icon i[data-post-id="' + postId + '"]').css("color", "red");
                   }
                 },
                 error: function(error) {
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
                    url: '/bookmark/registerBookmark',
                    data: {
                        postId: postId,
                    },
                    success: function(response) {
                      if (response.success) {
                         alert(response.message);
                         $('div.feed_footer_bookmark i[data-post-id="' + postId + '"]').prop('class', 'fa-regular fa-bookmark');
                      }
                    },
                    error: function(error) {
                        console.log(error);
                    }
                });
    } else if (className === "fa-regular fa-bookmark") {
           $.ajax({
                 type: 'GET',
                 url: '/bookmark/registerBookmark',
                 data: {
                     postId: postId,
                 },
                 success: function(response) {
                   if (response.success) {
                      alert(response.message)
                      $('div.feed_footer_bookmark i[data-post-id="' + postId + '"]').prop('class', 'fa-solid fa-bookmark');
                   }
                 },
                 error: function(error) {
                     console.log(error);
                 }
             });
    }
});

$(document).on('click', ".feed_footer_emoteButton .fa-face-smile", function (event) {

    var emoticonBox = $(this).closest('.feed_footer_emoteButton').find('.emoticonbox');
    emoticonBox.css('top', -emoticonBox.height() + 'px');

    event.stopPropagation();

    emoticonBox.toggle();
});


$(document).on('click', function (event) {
    if (!$(event.target).hasClass('fa-face-smile') && $(event.target).closest('.emoticonbox').length === 0) {
        $(".feed_footer_emoteButton .emoticonbox").hide();
    }
});

$(document).on('click', ".emoticon", function () {
    var emoticonText = $(this).text();
    var postCommentInput = $(this).closest("form").find(".feed_footer_postComment1")
    var currentText = postCommentInput.val();
    postCommentInput.val(currentText + emoticonText);
    postCommentInput.focus();
});

$(document).on('click', ".feed_footer_emoteButton .fa-face-smile", function (event) {

    var emoticonBox = $(this).closest('.feed_footer_emoteButton').find('.emoticonbox2');


    event.stopPropagation();


    emoticonBox.toggle();
});


$(document).on('click', function (event) {

    if (!$(event.target).hasClass('fa-face-smile') && $(event.target).closest('.emoticonbox2').length === 0) {
        $(".feed_footer_emoteButton .emoticonbox2").hide();
    }
});


$(document).on('click', ".emoticon", function () {

    var emoticonText = $(this).text();
    var postCommentInput = $(this).closest("form").find(".feed_footer_postComment2")
    var currentText = postCommentInput.val();
     postCommentInput.val(currentText + emoticonText);


    postCommentInput.focus();
});

$(document).on('keypress', '.feed_footer_postComment2', function(event) {
    if (event.which === 13) {
        event.preventDefault();

        var comment = $(this).val();
        var postId = $(this).closest('form').find('input[type="hidden"]').val();
        if(!comment.trim()) {
            return
        }
        $.ajax({
            type: 'GET',
            url: '/comment/registerComment',
            data: {
                comment: comment,
                postId: postId
            },
             success: function (response) {
                            if (response.success) {
                                var html = `
                                    <div class="explore_feed_head_box">
                                        <div class="explore_feed_head_imgbox">
                                            <a href="#" class="explore_feed_head_img">
                                                <img src="${response.append.url}" alt="">
                                            </a>
                                        </div>
                                        <div class="explore_feed_infobox">
                                            <div class="explore_feed_infobox1">${response.append.name}</div>
                                            <div class="explore_feed_infobox2">${response.append.content}</div>
                                            <div class="explore_feed_infobox2">${response.append.date}</div>
                                        </div>
                                    </div>
                                `;
                                $('.explore_feed_scrolls').append(html);
                            }
                        },
            error: function(error) {

                console.log(error);
            }
        });


        $(this).val('');
    }
});

$(document).on('click', ".menu_list_align button",function() {
    var postId = $(this).closest('article').attr('id').substring(4);
    var btnTxt = $(this).text();
    console.log(btnTxt + " : " + postId);
    if (btnTxt === '삭제') {
         $.ajax({
                    type: 'GET',
                    url: '/post/deletePost',
                    data: {
                        postId: postId
                    },
                     success: function (response) {
                        alert(response.message);
                     },
                    error: function(error) {

                        console.log(error);
                    }
                });
    }
})

function deletePost(e){
 var postId = $(e).closest('article').attr('id');
    var btnTxt = $(e).text();
    console.log(btnTxt + " : " + postId);

//    if (btnTxt === '삭제') {
//        $.ajax({
//            type: 'GET',
//            url: '/post/deletePost',
//            data: {
//                postId: postId
//            },
//            success: function(response) {
//                alert(response.message);
//            },
//            error: function(error) {
//                // 오류가 발생한 경우에 대한 처리
//                console.log(error);
//            }
//        });
//    }
}

