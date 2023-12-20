$('.nav_load').load('/view/navbar');

$(document).on('click', ".fa-heart", function() {
    var postId = $(this).data("post-id");
    var className = $(this).attr('class');
    var $this = $(this);
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
                         $this.text(response.count);
                         $this.prop('class', 'fa-regular fa-heart');
                          $this.css("color", "white");
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
                       $this.text(response.count);
                       $this.prop('class', 'fa-solid fa-heart');
                       $this.css("color", "red");
                   }
                 },
                 error: function(error) {
                     console.log(error);
                 }
             });
    }
});