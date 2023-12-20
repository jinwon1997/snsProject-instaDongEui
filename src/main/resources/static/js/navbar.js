// -------------------------for more option in navbar-------------------------

var MenuItem = $('#nav_submenu');
var searchBox = $('#search_box');
var mainLogo = $('.logo_img');
var miniLogo = $('#mini_insta_icon');
var vw

$(document).on('change', function() {
    vw = $(window).width();
});

$('#submenu_btn').click(function () {
    if (MenuItem.css('display') === "none") {
        MenuItem.css('display', 'block');
    } else {
        MenuItem.css('display', 'none');
    }
});

function animateDisplay(selectedElement) {
    if (selectedElement.css('display') == 'none') {
        setTimeout(function () {
            selectedElement.fadeIn(300).css('display', 'block');

        }, 300);
    } else {
        setTimeout(function () {
            selectedElement.fadeIn(300).css('display', 'none');
        }, 300);
    }
}

function aniSearchMove(selectedElement) {
    if (selectedElement.css('display') == 'none') {
        setTimeout(function () {
            selectedElement.css('display', 'block');
            selectedElement.animate({
                left: '70px',
            }, 400);
        }, 200);
    } else {
        setTimeout(function () {
            selectedElement.animate({
                left: '-400px',
            }, 400);
        }, 200);
        setTimeout(function() {
            selectedElement.css('display', 'none');
        }, 500);
    }
}

function searchToggle() {

    if (searchBox.css('display') == 'none') {
        MenuItem.css('display', 'none');
        aniSearchMove(searchBox);
        animateDisplay(mainLogo);
        animateDisplay(miniLogo);

    } else{
        MenuItem.css('display', 'none');
        aniSearchMove(searchBox);
        animateDisplay(mainLogo);
        animateDisplay(miniLogo);
    }
    if (searchBox.css('display') == 'block') {
        $('#navbar_box').animate({
            width: '250px',
        }, 500);
        setTimeout(function () {
            $('.box_for_span').fadeIn(300).css('display', 'block');
        }, 300);
    } else {
        $('.box_for_span').css('display', 'none');
        $('#navbar_box').animate({
            width: '70px',
        }, 500);
    }
}

function dmToggle() {
    if (dmBox.style.display === 'none') {
        dmBox.style.display = 'block';
        searchBox.style.display = 'none'; // 검색창 숨김
        moreoption.style.display = 'none'; // moreopt 숨김
    } else {
        dmBox.style.display = 'none';
    }
}

$(document).ready(function () {
    $(window).resize(function () {
        if ($("#nav_submenu").css('display') === 'block')
            var sub_btn_posY =
                $("#submenu_btn").offset().top - $("#submenu_ul").outerHeight() + 50;
        $("#nav_submenu").css('top', sub_btn_posY);

    })
});

var searchTimeOut;

$(document).on('keyup', '.search_input', function (event) {
    var search_input = $(this).val().trim();

    clearTimeout(searchTimeOut);

    if (search_input.length > 0) {
        searchTimeOut = setTimeout(function () {
            $.ajax({
                type: 'GET',
                url: '/test/SearchUserResults',
                data: {
                    search_input: search_input,
                },
                success: function (response) {

                    var searchResultContainer = $('#search_result');
                    searchResultContainer.empty();

                    if (response.result && response.result.length > 0) {
                        response.result.forEach(function (result) {
                            var outerDiv = $('<div>').addClass('search_otherUser_box1');
                            var profileBox = $('<div>').addClass('search_profile_box');
                            var profileAlign = $('<div>').addClass('search_profile_align');
                            var link = $('<a>').attr('href', '#').addClass('search_profile_link');
                            var img = $('<img>').attr('src', result.url).attr('alt', '').addClass('search_profile_img');
                            var userinfoBox = $('<div>').addClass('search_userinfo_box');
                            var userName = $('<div>').addClass('search_userName');
                            var userNameLink = $('<span>').attr('href', '#').addClass('search_userName_link').text(result.user_name);
                            var name = $('<div>').addClass('search_name');
                            var nameText = $('<span>').addClass('search_name_text').text(result.name);

                            userName.append(userNameLink);
                            name.append(nameText);
                            userinfoBox.append(userName);
                            userinfoBox.append(name);
                            profileAlign.append(link.append(img));
                            profileBox.append(profileAlign);
                            outerDiv.append(profileBox);
                            outerDiv.append(userinfoBox);

                            searchResultContainer.append(outerDiv);
                        });
                    } else {
                        searchResultContainer.append('<div class="no-users"><span>일치하는 유저가 없습니다</span></div>');
                                                                                                                     }
                                                                                                                 },
                                                                                                                 error: function (error) {
                                                                                                                     // Handle the error
                                                                                                                     console.log('Search failed:', error);
                                                                                                                 }
                                                                                                             });
                                                                                                         }, 300);
                                                                                                     } else {
                                                                                                         $('#search_result').empty();
                                                                                                     }
                                                                                                 });
