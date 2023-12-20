document.getElementById('myModal').style.display = 'none';

var clicked = "";
        function followAJAX(){
            $.ajax({
                    type: 'GET',
                    url: '/view/profile/follow',
                    data: {
                        //search_input: search_input,
                    },
            }).done(function(response){
               $("#follower_list").html(response);
           });

        }

        function openModalFollow() {
                clicked = document.getElementById('follow_text').value;
                document.getElementById('follower_title').innerHTML = "내가 팔로우한 유저"
                //console.log(clicked);

                document.getElementById('myModal').style.display = 'block';
                document.getElementById('modalBackdrop').style.display = 'block';
                followAJAX();

            }

        function followerAJAX(){
            $.ajax({
                    type: 'GET',
                    url: '/view/profile/follower',
                    data: {
                        //search_input: search_input,
                    },
            }).done(function(response){
               $("#follower_list").html(response);
           });
        }

        function openModalFollower() { // 처음에 팔로워 눌렀을 때 뜨는 창
            clicked = document.getElementById('follower_text').value;
            document.getElementById('follower_title').innerHTML = "나를 팔로우한 유저"
            //console.log(clicked);
            document.getElementById(follower_title);
            document.getElementById('myModal').style.display = 'block';
            document.getElementById('modalBackdrop').style.display = 'block';
            followerAJAX();
        }

       $(document).on("click", 'button.follower_button', function(){
          var follow_member_id = $(this).attr("value");
          //console.log("id = "+follow_member_id);

          $.ajax({
                  type: 'GET',
                  url: '/view/profile/followbutton',
                  data: {
                      follow_member_id: follow_member_id,
                  },
          }).done(function(response){
             $(".follower_list").html(response); //  .follower_list #follower_click
         });

       });
/*
       $('.category').click(function(){
         var id_check = $(this).attr("id");
         console.log("id = "+id_check);
       });
*/

        function closeModal() {
          clicked = "";
          document.getElementById('follower_title').innerHTML = "";
          document.getElementById('myModal').style.display = 'none';
          document.getElementById('modalBackdrop').style.display = 'none';
          location.reload();
        }



var searchTimeOut;

$(document).on('keyup', '#temp_div_for_align2 .search_input', function (event) {
    var search_input = $(this).val().trim();
    //console.log("search_input : " + search_input);
    clearTimeout(searchTimeOut);

    if (search_input.length > 0 && clicked == "follower") {
    setTimeout(() =>
            $.ajax({
                    type: 'GET',
                    url: '/view/profile/followersearch',
                    data: {
                        search_input: search_input,
                    },
                    success: function (response) {
                        $(".follower_list").html(response);
                    },
                    error: function (error) {
                        // Handle the error
                        console.log('Search failed:', error);
                    }
            })
    , 300);


    } else if(search_input.length > 0 && clicked == "follow"){

         setTimeout(() =>
                $.ajax({
                        type: 'GET',
                        url: '/view/profile/followsearch',
                        data: {
                            search_input: search_input,
                        },
                        success: function (response) {
                            $(".follower_list").html(response);
                        },
                        error: function (error) {
                            // Handle the error
                            console.log('Search failed:', error);
                        }
                })
        , 300);

    } else {
        $('#search_result').empty();

        if(clicked =="follower"){
            followerAJAX();
        }
        else if(clicked =="follow"){
            followAJAX();
        }
    }
});
