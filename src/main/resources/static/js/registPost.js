var user_src;
var user_id;

$.ajax({
   url: '/registPost/getPostUser', // 서버 업로드를 처리하는 엔드포인트 URL
   type: 'GET',
   contentType: false,
   processData: false,
   success: function (response) {
      if (response.success) {
         user_id = response.userId;
         user_src = response.url;
      } else {
         alert("누구세요? 로그인 정보를 찾을 수 없어요!");
      }
   },
   error: function (error) {
      console.log(error);
   }
});

$('#edit_img_add_box #edit_img_add_button').on('click', function () {
   $('#edit_img_input').click();
});

var newWidth = [];
var newHeight = [];
var aspectRatio = [];
var mode = 'UPLOAD';
var images = [];
var original_img = [];
var currentScale = [];
var imageIndex = 0;
var fileDatas = [];
var reader = [];
var canvases = [];
var fileLength;
$('#edit_img_add_box #edit_img_input').on('change', function () {

   reader = [];
   var selectedFiles = [];
   fileLength = this.files.length;
   for (var i = 0; i < fileLength; i++) {
      reader[i] = new FileReader();
      imageIndex = i;
      currentScale[i] = 1;
      images[i] = new Image();
      newX[i] = 0;
      newY[i] = 0;
      newWidth[i] = 0;
      newHeight[i] = 0;
      aspectRatio[i] = 0;
      selectedFiles[i] = this.files[i];
      camanIndex[i] = 6;
   }

   $('#edit_img_title_container').css('z-index', '1');
   $('#edit_img_title_leftWrap').css('display', 'flex');
   $('#edit_img_title_leftWrap').css('z-index', '2');
   $('#edit_img_title_rightWrap').css('display', 'flex');
   $('#edit_img_title_rightWrap').css('z-index', '2');
   $('#edit_img_title').text('자르기');

   $('#edit_img_add_padding').css('display', 'none');

   var editField = $("<div>").addClass("edit_field_container");
   var interactiveBox = $("<div>").addClass("edit_field_box");
   var editImg = $("<div>").addClass("edit_field_img");
   var editDiv = $("<div>").addClass("edit_field_div");
   var editScale = $("<div>").addClass("edit_field_scale_box");
   var zoomBox = $("<div>").addClass("edit_zoom_box");
   var zoomInput = $("<input>").addClass("edit_zoom_input");
   var prevBox = $("<div>").addClass("edit_prev_img_box");
   var nextBox = $("<div>").addClass("edit_next_img_box");

   $(zoomInput).attr({
      'type': 'range',
      'min': '0',
      'max': '100',
      'readonly': true,
      'value': '0'
   });

   $('#edit_img_add_box').append(editField);
   $('.edit_field_container').append(interactiveBox);
   $('.edit_field_box').append(editImg);
   $('.edit_field_box').append(editDiv);
   $('.edit_field_box').append(editScale);
   $('.edit_field_scale_box').append('<i class="fa-solid fa-magnifying-glass-plus"></i>');
   $('.edit_field_box').append(zoomBox);
   $('.edit_zoom_box').append(zoomInput);
   $('#edit_img_add_box').append(prevBox);
   $('.edit_prev_img_box').append('<i class="fa-solid fa-chevron-left"></i>');
   $('#edit_img_add_box').append(nextBox);
   $('.edit_next_img_box').append('<i class="fa-solid fa-chevron-right"></i>');

   $('.edit_field_container').css('overflow', 'hidden');
   $('.edit_field_img').css('background-position', 'center center');
   $('.edit_field_img').css('background-repeat', 'no-repeat');
   $('.edit_field_img').css('background-size', 'cover');
   $('.edit_field_img').css('overflow', 'hidden');

   $('.edit_field_container').css('display', 'block');

   $('.edit_field_scale_box').on('click', function () {
      if ($('.edit_zoom_box').css('display') == 'none') {
         $('.edit_zoom_box').fadeIn(300, function () {
            $('.edit_zoom_box').css('display', 'flex');
         });
      } else if ($('.edit_zoom_box').css('display') == 'flex') {
         $('.edit_zoom_box').fadeOut(300, function () {
            $('.edit_zoom_box').css('display', 'none');
         })
      }
   });

   $('.edit_zoom_input').on('input', function () {
      currentScale[imageIndex] = $(this).val() * 0.01 + 1;
      var currentTransform = $('.edit_field_img').css('transform');
      var tempX = ((newWidth[imageIndex] * currentScale[imageIndex]) - minWidth) / 2;
      var tempY = ((newHeight[imageIndex] * currentScale[imageIndex]) - minHeight) / 2;
      if (newX[imageIndex] > tempX) {
         newX[imageIndex] = tempX;
      } else if (newX[imageIndex] < -tempX) {
         newX[imageIndex] = -tempX;
      }
      if (newY[imageIndex] > tempY) {
         newY[imageIndex] = tempY;
      } else if (newY[imageIndex] < -tempY) {
         newY[imageIndex] = -tempY;
      }
      var newTransform = currentTransform === 'none' ? 'scale(' + currentScale[imageIndex] + ')' : 'translate3d(' + newX[imageIndex] + 'px, ' + newY[imageIndex] + 'px, 0)' + ' scale(' + currentScale[imageIndex] + ')';

      $('.edit_field_img').css('transform', newTransform);
   });

   if (fileLength > 1) {
      $('.edit_next_img_box').css('display', 'flex');
   }


   /* Image move button */
   /************************************************/
   $('.edit_prev_img_box').on('click', function () {
      imageIndex--;
      if (imageIndex == 0) {
         $('.edit_prev_img_box').css('display', 'none');
      } else {
         $('.edit_prev_img_box').css('display', 'flex');
      }
      if (mode == 'EDIT') {
         photoEdit();
      } else if (mode == 'PHOTOSHOP') {
         $('.edit_photo_canvas').css('display', 'none');
         $('.edit_photo_canvas').eq(imageIndex).css('display', 'block');
      } else if (mode == 'POSTING') {
         $('.edit_photo_canvas').css('display', 'none');
         $('.edit_photo_canvas').eq(imageIndex).css('display', 'block');
      }
      $('.edit_next_img_box').css('display', 'flex');
   });

   $('.edit_next_img_box').on('click', function () {
      imageIndex++;
      if ((imageIndex + 1) == fileLength) {
         $('.edit_next_img_box').css('display', 'none');
      } else {
         $('.edit_next_img_box').css('display', 'flex');
      }
      if (mode == 'EDIT') {
         photoEdit();
      } else if (mode == 'PHOTOSHOP') {
         $('.edit_photo_canvas').css('display', 'none');
         $('.edit_photo_canvas').eq(imageIndex).css('display', 'block');
      } else if (mode == 'POSTING') {
         $('.edit_photo_canvas').css('display', 'none');
         $('.edit_photo_canvas').eq(imageIndex).css('display', 'block');
      }
      $('.edit_prev_img_box').css('display', 'flex');
   });
   /****************************************************/
   /* Image move button */



   for (var i = 0; i < fileLength; i++) {
      imageIndex = i;
      reader[i].onload = createReaderOnLoadHandler(i);
      reader[i].readAsDataURL(selectedFiles[i]);
   }

   imageIndex = 0;
   mode = 'EDIT';
});


/** Photo mode selector
/************************************************/
$('#edit_img_title_cover').on('click', function () {
   if (mode == 'EDIT') {
      mode = 'UPLOAD';
      $('#edit_img_title_leftWrap').css('display', 'none');
      $('#edit_img_title_rightWrap').css('display', 'none');
      $('.edit_field_container').remove();
      $('.edit_prev_img_box').remove();
      $('.edit_next_img_box').remove();
      $('#edit_img_title').text('새 게시물 만들기');
      $('#edit_img_add_padding').css('display', 'flex');
      $('#edit_img_input').val('');
   } else if (mode == 'PHOTOSHOP') {
      mode = 'EDIT';
      $('#edit_img_title').text('자르기');
      $('.edit_field_container').css('display', 'block');
      $('#edit_img_add_container').css('flex-direction', 'column');
      $('#edit_img_add_container').css('flex-grow', '0');
      $('.edit_photo_canvas').remove();
      $('.edit_photo_filter_wrap').remove();
      $('#edit_img_align5').animate({
         width: '730px'
      }, 400);
      photoEdit();
   } else if (mode == 'POSTING') {
      mode = 'PHOTOSHOP';
      $('.edit_photo_filter_wrap').remove();
      $('#edit_img_title').text('편집');
      $('#edit_img_title_rightBox').text('다음');
      filterArea();
   }
});

$('#edit_img_title_rightBox').on('click', async function () {
   if (mode == 'EDIT') {
      mode = 'PHOTOSHOP';
      $('#edit_img_align5').animate({
         width: '1070px'
      }, 400);
      $('.edit_field_container').css('display', 'none');
      $('#edit_img_add_container').css('flex-direction', 'row');
      $('#edit_img_add_container').css('flex-grow', '1');
      $('#edit_img_title').text('편집');

      for (var i = 0; i < fileLength; i++) {
         var canvas = $('<canvas>').addClass("edit_photo_canvas");
         $('#edit_img_add_box').append(canvas);
         canvases.push(canvas);
         drawImage(canvas, i);
      }
      $('.edit_photo_canvas').css('display', 'none');
      $('.edit_photo_canvas').eq(imageIndex).css('display', 'block');
      filterArea();
   } else if (mode == 'PHOTOSHOP') {
      mode = 'POSTING';
      $('#edit_img_title').text('새 게시물 만들기');
      $('.edit_photo_filter_box').empty();
      textArea();
   } else if (mode == 'POSTING') {
      var formData = new FormData();
      var text = $('.edit_text_comment').val();

      for (var i = 0; i < fileLength; i++) {
         imageIndex = i;
         var imageData = $('.edit_photo_canvas')[i].toDataURL("image/png");

         var blobBin = atob(imageData.split(',')[1]);
         var array = [];

         for (var j = 0; j < blobBin.length; j++) {
            array.push(blobBin.charCodeAt(j));
         }

         var file = new Blob([new Uint8Array(array)], {
            type: 'image/png'
         });
         formData.append("file", file);
      }
      formData.append("text", text);


      $.ajax({
         url: '/registPost/upload', // 서버 업로드를 처리하는 엔드포인트 URL
         type: 'POST',
         data: formData,
         contentType: false,
         processData: false,
         success: function (response) {
            if (response.success) {
               window.location.href = "/view/home";
            } else {
               alert("머한거냐");
            }
         },
         error: function (error) {
            console.log(error);
         }
      });
   }
});
/** Photo mode selector
/************************************************/



function photoEdit() {
   $('.edit_field_img').css('background-image', 'url(' + fileDatas[imageIndex] + ')');
   $('.edit_field_img').css({
      'transform': 'translate3d(' + newX[imageIndex] + 'px, ' + newY[imageIndex] + 'px, 0)' + ' scale(' + currentScale[imageIndex] + ')'
   });
   setTimeout(function () {
      $('.edit_field_img').css('width', `${newWidth[imageIndex]}px`);
      $('.edit_field_img').css('height', `${newHeight[imageIndex]}px`);
   }, 100);
   $('.edit_zoom_input').val(currentScale[imageIndex]);
   $('.edit_zoom_input').change();
   images[imageIndex].src = fileDatas[imageIndex];
}

function createReaderOnLoadHandler(index) {
   return function (e) {
      const fileData = e.target.result;
      fileDatas[index] = fileData;

      $('.edit_field_img').css('background-image', 'url(' + fileDatas[0] + ')');

      images[index].onload = function () {
         aspectRatio[index] = images[index].width / images[index].height;

         newWidth[index] = Math.max(730, aspectRatio[index] * 730);
         newHeight[index] = 730;

         $('.edit_field_img').css('width', `${newWidth[0]}px`);
         $('.edit_field_img').css('height', `${newHeight[0]}px`);
      };

      images[index].src = fileData;
   };
}


var isDragging = false;
var initialMouseX = [];
var initialMouseY = [];
var initialElementX, initialElementY;
var minWidth = 730;
var minHeight = 730;
var newX = [];
var newY = [];
$(document).on('mousedown', '.edit_field_div', function (e) {
   isDragging = true;
   initialMouseX = e.clientX;
   initialMouseY = e.clientY;

   $('.edit_field_div').css('background-image', 'linear-gradient(#ddd 2px, transparent 3px), linear-gradient(to right, #ccc 2px, transparent 3px)');

   var transformMatrix = $('.edit_field_img').css('transform').replace(/[^0-9\-.,]/g, '').split(',');
   initialElementX = parseInt(transformMatrix[4]) || 0;
   initialElementY = parseInt(transformMatrix[5]) || 0;

});

$(document).on('mousemove', function (e) {
   if (isDragging) {
      var offsetX = (e.clientX - initialMouseX) / 3;
      var offsetY = (e.clientY - initialMouseY) / 3;

      newX[imageIndex] = initialElementX + offsetX;
      newY[imageIndex] = initialElementY + offsetY;

      $('.edit_field_img').css({
         'transform': 'translate3d(' + newX[imageIndex] + 'px, ' + newY[imageIndex] + 'px, 0)' + ' scale(' + currentScale[imageIndex] + ')'
      });
   }
});

$(document).on('mouseup', function () {
   if (isDragging) {
      isDragging = false;
      $('.edit_field_div').css('background-image', 'none');
      var tempX = ((newWidth[imageIndex] * currentScale[imageIndex]) - minWidth) / 2;
      var tempY = ((newHeight[imageIndex] * currentScale[imageIndex]) - minHeight) / 2;
      if (newX[imageIndex] > tempX) {
         newX[imageIndex] = tempX;
      } else if (newX[imageIndex] < -tempX) {
         newX[imageIndex] = -tempX;
      }
      if (newY[imageIndex] > tempY) {
         newY[imageIndex] = tempY;
      } else if (newY[imageIndex] < -tempY) {
         newY[imageIndex] = -tempY;
      }

      $('.edit_field_img').css({
         'transform': 'translate3d(' + newX[imageIndex] + 'px, ' + newY[imageIndex] + 'px, 0)' + ' scale(' + currentScale[imageIndex] + ')'
      });
   }
});

function drawImage(canvas, index) {
   const cvs = canvas[0];
   const ctx = cvs.getContext('2d');
   cvs.width = 730;
   cvs.height = 730;

   var tempVar = images[index].width < images[index].height ? images[index].width : images[index].height;
   tempVar = tempVar / currentScale[index];
   var tempRatio = tempVar / 730;
   imageX = ((images[index].width - tempVar) / 2) - (newX[index] * tempRatio);
   imageY = ((images[index].height - tempVar) / 2) - (newY[index] * tempRatio);

   ctx.drawImage(images[index],
      imageX, imageY, tempVar, tempVar,
      0, 0, cvs.width, cvs.height);
   original_img[index] = ctx.getImageData(0, 0, cvs.width, cvs.height);
   cammanFilter(camanIndex[index]);
};

function filterArea() {
   var wrap = $('<div>').addClass('edit_photo_filter_wrap');
   var cont = $('<div>').addClass('edit_photo_filter_container');
   var box = $('<div>').addClass('edit_photo_filter_box');
   var head = $('<div>').addClass('edit_photo_filter_head');
   var head_p = $('<div>').addClass('edit_photo_filter_head_pack');
   var head_c = $('<div>').addClass('edit_photo_filter_head_custom');
   var body_cont = $('<div>').addClass('edit_photo_filter_body_container');
   var body_box = $('<div>').addClass('edit_photo_filter_body_box');

   $('#edit_img_add_container').append(wrap);
   $(wrap).append(cont);
   $(cont).append(box);
   $(box).append(head);
   $(box).append(body_cont);
   $(head).append(head_p);
   $(head).append(head_c);
   $(head_p).append($('<span>'));
   $(head_c).append($('<span>'));
   $(body_cont).append(body_box);

   $('.edit_photo_filter_head_pack span').text('필터');
   $('.edit_photo_filter_head_custom span').text('조정');

   var list = ['SnixJ', 'ZylxP', 'Yuzio', 'Grey', 'Quixz', 'Original'];
   var img_list = ['/img/first_filter.png', '/img/second_filter.png', '/img/third_filter.png'
                  , '/img/fourth_filter.png', '/img/fifth_filter.png', '/img/original.jpg']

   for (var i = 0; i < 6; i++) {
      var filter_list = $('<div>').addClass('edit_photo_filter_list');
      var filter_button = $('<button>').addClass('edit_photo_filter_btn');
      var filter_img_box = $('<div>').addClass('edit_photo_filter_imgBox');
      var filter_title = $('<div>').addClass('edit_photo_fiter_title');
      var filter_img = $('<img>').addClass('edit_photo_filter_img');
      $(filter_title).text(list[i]);
      $(filter_img).attr('src', img_list[i]);
      $(body_box).append(filter_list);
      $(filter_list).append(filter_button);
      $(filter_button).append(filter_img_box);
      $(filter_button).append(filter_title);
      $(filter_img_box).append(filter_img);
   }
}

function textArea() {
   var wrap = $('<div>').addClass('edit_text_name_wrap');
   var cont = $('<div>').addClass('edit_text_name_cont');
   var box = $('<div>').addClass('edit_text_name_box');
   var sep = $('<div>').addClass('edit_text_name_separate');
   var imgBox = $('<div>').addClass('edit_text_name_img_box');
   var imgSpan = $('<span>').addClass('edit_text_name_img_span');
   var img = $('<img>').addClass('edit_text_name_img');
   var textBox = $('<div>').addClass('edit_text_name_value_box');
   var text = $('<span>').addClass('edit_text_name_value');

   var commentCont = $('<div>').addClass('edit_text_comment_container');
   var commentBox = $('<div>').addClass('edit_text_comment_box');
   var comment = $('<textarea>').addClass('edit_text_comment');

   $('.edit_photo_filter_box').append(wrap);
   $(wrap).append(cont);
   $(cont).append(box);
   $(box).append(sep);
   $(sep).append(imgBox);
   $(imgBox).append(imgSpan);
   $(imgSpan).append(img);
   $(sep).append(textBox);
   $(textBox).append(text);
   $('.edit_photo_filter_box').append(commentCont);
   $(commentCont).append(commentBox);
   $(commentBox).append(comment);
   $(comment).attr('spellcheck', 'false');
   $(comment).attr('placeholder', '문구를 입력해주세요..');
   $(text).text(user_id);
   $(img).attr('src', user_src);
}

var camanInstance = [];
var camanIndex = [];
$(document).on('click', '.edit_photo_filter_btn', function () {
   var index = $('.edit_photo_filter_btn').index(this);
   if (camanInstance[imageIndex]) {
      camanInstance[imageIndex].revert();
   }
   camanIndex[imageIndex] = index;
   cammanFilter(index);
});

function cammanFilter(index) {
   var canvasSelect = document.querySelectorAll('.edit_photo_canvas')[imageIndex];
   switch (index) {
      case 0:
         camanInstance[imageIndex] = Caman(canvasSelect, function () {
            this.saturation(6);
            this.brightness(3);
            this.channels({
               red: 3,
               blue: 2,
               green: 4
            });
            this.stackBlur(2);
            this.render();
         });

         break;
      case 1:
         camanInstance[imageIndex] = Caman(canvasSelect, function () {
            this.hue(5);
            this.gamma(1);
            this.saturation(-5);
            this.brightness(10);
            this.clip(11);
            this.contrast(-5);
            this.sepia(5);
            this.noise(1);
            this.sharpen(10);
            this.render();
         });
         break;
      case 2:
         camanInstance[imageIndex] = Caman(canvasSelect, function () {
            this.saturation(-30);
            this.sepia(50);
            this.noise(8);
            this.render();
         });
         break;
      case 3:
         camanInstance[imageIndex] = Caman(canvasSelect, function () {
            this.greyscale();
            this.render();
         });
         break;
      case 4:
         camanInstance[imageIndex] = Caman(canvasSelect, function () {
            this.brightness(10);
            this.vibrance(-5);
            this.gamma(0.8);
            this.contrast(4);
            this.saturation(14);
            this.exposure(8);
            this.render();
         });
         break;
      case 5:
         camanInstance[imageIndex] = Caman(canvasSelect, function () {
            this.render();
         });
         break;
   };
};

$('#create_cancle_img_box').on('click', function() {
    window.location.href = '/view/home';
});